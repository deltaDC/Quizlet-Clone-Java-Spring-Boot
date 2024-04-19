package com.deltadc.quizletclone.card;

import  com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if(card.getFront_text().length() <= 0 || card.getBack_text().length() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("khong duoc de trong");
        }

        Card newCard = new Card();

        newCard.set_known(false);
        newCard.setFront_text(card.getFront_text());
        newCard.setBack_text(card.getBack_text());
        newCard.setSet_id(set.getSet_id());
        cardRepository.save(newCard);
        return ResponseEntity.ok(newCard);
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
//        set.removeCard(card); // Xóa Card ra khỏi Set
        cardRepository.deleteById(id);
        return ResponseEntity.ok("Deleted!");
    }

    public ResponseEntity<?> createCards(Long setId, List<Card> cardList) {

        Optional<Set> set = setRepository.findById(setId);

        if(set.isPresent()) {
            cardList.forEach(card -> card.setSet_id(setId));

            List<Card> savedCards = cardRepository.saveAll(cardList);
            return ResponseEntity.ok(savedCards);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Set khong ton tai");
        }

    }
}
