package com.deltadc.quizletclone.card;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    // Các phương thức xử lý Card
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    public Card createCard(Card card) {
        card.setFront_text(card.getFront_text());
        card.setBack_text(card.getBack_text());
        card.setCreated_at(card.getCreated_at());
        card.setUpdated_at(card.getUpdated_at());
        return cardRepository.save(card);
    }

    public Card updateCard(Long id, Card updatedCard) {
        Card existingCard = getCardById(id);
        if (existingCard != null) {
            existingCard.setFront_text(updatedCard.getFront_text());
            existingCard.setBack_text(updatedCard.getBack_text());
            existingCard.setUpdated_at(updatedCard.getUpdated_at());
            return cardRepository.save(existingCard);
        }
        return null;
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
