package com.deltadc.quizletclone.set;

import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetService {
    private final SetRepository setRepository;
    private final UserRepository userRepository;

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

    public ResponseEntity<?> createSet(@RequestBody String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            String title = node.get("title").asText();
            String description = node.get("description").asText();
            boolean isPublic = node.get("is_public").asBoolean();

            // Trích xuất thông tin người dùng từ JWT
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();


            // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
            User user = userRepository.findByEmail(username).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại");
            }

            Set createdSet = new Set();
            createdSet.setTitle(title);
            createdSet.setDescription(description);
            createdSet.setPublic(isPublic);
            createdSet.setUser(user);

            Set savedSet = setRepository.save(createdSet);

            // Trả về đối tượng set vừa được tạo trong phản hồi ResponseEntity
            return ResponseEntity.ok(savedSet);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể tạo set");
        }
    }

    public ResponseEntity<String> deleteSet(@PathVariable Long setId) {
        // Trích xuất thông tin người dùng từ JWT
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
        User user = userRepository.findByEmail(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại");
        }

        Set s = setRepository.findById(setId).orElse(null);
        if(s == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Set không tồn tại");
        }

        if (!s.getUser().getUser_id().equals(user.getUser_id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không được phép xóa");
        }

        setRepository.deleteById(setId);
        return ResponseEntity.ok("Xóa set thành công");
    }
}
