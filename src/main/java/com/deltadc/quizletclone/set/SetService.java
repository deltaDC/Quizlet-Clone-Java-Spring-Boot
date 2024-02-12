package com.deltadc.quizletclone.set;

import com.deltadc.quizletclone.card.Card;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetService {
    private final SetRepository setRepository;

    public ResponseEntity<List<Card>> getCardsInSet(Long setId, String userId) {
        // Kiểm tra xem setId có tồn tại trong cơ sở dữ liệu không
        Set set = setRepository.findById(setId).orElse(null);
        if (set == null) {
            // Nếu setId không tồn tại, trả về 404 Not Found
            return ResponseEntity.notFound().build();
        }

        // Kiểm tra xem người dùng hiện tại có quyền truy cập vào set không
        // so sánh userId của set với userId của người dùng
        if (!set.getUser().getUser_id().toString().equals(userId)) {
            // Nếu người dùng không có quyền truy cập vào set, trả về 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Nếu người dùng có quyền truy cập vào set, bạn có thể lấy danh sách card thuộc về set và trả về
        List<Card> cards = set.getCards();
        return ResponseEntity.ok(cards);
    }

    public ResponseEntity<String> createSet(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Card> cardList = mapper.readValue(json, new TypeReference<List<Card>>() {});

            Set createdSet = new Set();

            for(Card c : cardList) {
                c.setSet(createdSet);
                createdSet.addCard(c);
            }

            setRepository.save(createdSet);

            return ResponseEntity.ok("Tạo set thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể tạo set");
        }
    }
}
