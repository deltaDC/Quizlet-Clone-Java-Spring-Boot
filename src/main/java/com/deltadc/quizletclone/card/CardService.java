package com.deltadc.quizletclone.card;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    // Các phương thức xử lý Card
    public ResponseEntity<List<Card>> getAllCards() {
        return ResponseEntity.ok(cardRepository.findAll());
    }

    public ResponseEntity<?> getCardById(Long id) {
        Card card = cardRepository.findById(id).orElse(null);
        return ResponseEntity.ok(Objects.requireNonNullElse(card, "Not found"));
    }

    public ResponseEntity<?> createCard(Card card) {
        card.setFront_text(card.getFront_text());
        card.setBack_text(card.getBack_text());
        card.onCreate();
        card.onUpdate();
        cardRepository.save(card);
        return ResponseEntity.ok("Created card!");
    }

    public ResponseEntity<?> updateCard(Long id, Card updatedCard) {
        Card existingCard = cardRepository.findById(id).orElse(null);
        if (existingCard != null) {
            existingCard.setFront_text(updatedCard.getFront_text());
            existingCard.setBack_text(updatedCard.getBack_text());
            existingCard.onUpdate();
            cardRepository.save(existingCard);
            return ResponseEntity.ok("Updated card!");
        }
        return ResponseEntity.ok("Not found!");
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
