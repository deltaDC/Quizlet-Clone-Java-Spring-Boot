package com.deltadc.quizletclone.card;

import  com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final SetRepository setRepository;

    // Các phương thức xử lý Card
    public ResponseEntity<?> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        List<CardDTO> cardDTOS = new ArrayList<>();
        for (Card card : cards) {
            cardDTOS.add(new CardDTO(card));
        }
        return ResponseEntity.ok(cardDTOS);
    }

    public ResponseEntity<?> getCardById(Long id) {
        Card card = cardRepository.findById(id).orElse(null);
        if (card == null) {
            return ResponseEntity.badRequest().body("Cannot find this card!");
        }
        CardDTO cardDTO = new CardDTO(card);
        return ResponseEntity.ok(cardDTO);
    }

    // Lấy tất cả cards trong 1 set
//    public ResponseEntity<?> getCardsInSet(Long set_id) {
//        Set set = setRepository.findById(set_id).orElse(null);
//        if (set == null) {
//            return ResponseEntity.badRequest().body("Cannot find this set!");
//        }
//        List<Card> cards = set.getCards();
//        List<CardDTO> cardDTOS = new ArrayList<>();
//        for (Card card : cards) {
//            cardDTOS.add(new CardDTO(card));
//        }
//        return ResponseEntity.ok(cardDTOS);
//    }

    public ResponseEntity<?> getCardsInSet(Long set_id, int page, int size) {
        Set set = setRepository.findById(set_id).orElse(null);
        if (set == null) {
            return ResponseEntity.badRequest().body("Cannot find this set!");
        }
        Page<Card> cards = cardRepository.findCardsBySetId(set_id, PageRequest.of(page, size));
        Page<CardDTO> cardDTOS = cards.map(CardDTO::new);
        return ResponseEntity.ok(cardDTOS);
    }

    public ResponseEntity<?> createCard(Card card, Long set_id) {
        Set set = setRepository.findById(set_id).orElse(null);
        if (set == null) {
            return ResponseEntity.badRequest().body("Cannot find this set!");
        }
        card.set_known(false);
        card.setFront_text(card.getFront_text());
        card.setBack_text(card.getBack_text());
        card.setSet(set);
        card.onCreate();
        card.onUpdate();
        cardRepository.save(card);
        set.addCard(card);      // Thêm card vào trong set
        return ResponseEntity.ok(new CardDTO(card));
    }

    public ResponseEntity<?> updateCard(Long id, Card updatedCard) {
        Card existingCard = cardRepository.findById(id).orElse(null);
        if (existingCard != null) {
            existingCard.setFront_text(updatedCard.getFront_text());
            existingCard.setBack_text(updatedCard.getBack_text());
            existingCard.onUpdate();
            cardRepository.save(existingCard    );
            return ResponseEntity.ok(new CardDTO(existingCard));
        }
        return ResponseEntity.badRequest().body("Cannot find card");
    }

    public ResponseEntity<?> deleteCard(Long id) {
        Card card = cardRepository.findById(id).orElse(null);
        if (card == null) {
            return ResponseEntity.badRequest().build();
        }
        Set set = card.getSet();
        set.removeCard(card); // Xóa Card ra khỏi Set
        cardRepository.deleteById(id);
        return ResponseEntity.ok("Deleted!");
    }
}
