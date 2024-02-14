package com.deltadc.quizletclone.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    // Các phương thức xử lý Card
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    public Card createCard(Card card) {
        card.setCreated_at(new Date());
        card.setUpdated_at(new Date());
        return cardRepository.save(card);
    }

    public Card updateCard(Long id, Card updatedCard) {
        Card existingCard = getCardById(id);
        if (existingCard != null) {
            existingCard.setFront_text(updatedCard.getFront_text());
            existingCard.setBack_text(updatedCard.getBack_text());
            existingCard.setUpdated_at(new Date());
            return cardRepository.save(existingCard);
        }
        return null;
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
