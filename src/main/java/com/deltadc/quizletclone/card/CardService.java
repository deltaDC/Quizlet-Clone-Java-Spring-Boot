package com.deltadc.quizletclone.card;

import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final SetRepository setRepository;

    public List<CardDTO> getAllCards() {
        return cardRepository.findAll().stream()
                .map(CardDTO::fromCardToCardDTO).toList();
    }

    public CardDTO getCardById(Long id) {
        return cardRepository.findById(id)
                .map(CardDTO::fromCardToCardDTO)
                .orElseThrow();
    }

    public Page<CardDTO> getCardsInSet(Long set_id, int page, int size) {
        setRepository.findById(set_id).orElseThrow();
        List<CardDTO> cards = cardRepository.findCardsBySetId(set_id, PageRequest.of(page, size)).stream()
                .map(CardDTO::fromCardToCardDTO).toList();

        return new PageImpl<>(cards, PageRequest.of(page, size), cards.size());
    }

    public Card createCard(Card card, Long set_id) throws Exception {
        setRepository.findById(set_id).orElseThrow();

        if(card.getFront_text().isEmpty() || card.getBack_text().isEmpty()) {
            throw new Exception("card should not be empty");
        }

        Card newCard = Card.builder()
                .set_id(set_id)
                .front_text(card.getFront_text())
                .back_text(card.getBack_text())
                .is_known(false)
                .build();

        return cardRepository.save(newCard);
    }

    public CardDTO updateCard(Long id, Card updatedCard) {

        Card existingCard = cardRepository.findById(id).orElseThrow();

        existingCard.setFront_text(updatedCard.getFront_text());
        existingCard.setBack_text(updatedCard.getBack_text());
        existingCard.onUpdate();
        cardRepository.save(existingCard);

        return CardDTO.fromCardToCardDTO(existingCard);
    }

    public void deleteCard(Long id) {
        cardRepository.findById(id).orElseThrow();
        cardRepository.deleteById(id);
    }

    public List<Card> createCards(Long setId, List<Card> cardList) throws NoSuchElementException {
        Set set = setRepository.findById(setId)
                .orElseThrow(() -> new NoSuchElementException("Set not found"));

        cardList.forEach(card -> card.setSet_id(set.getSet_id()));

        return cardRepository.saveAll(cardList);
    }

    public List<Card> getListCardsBySetId(Long setId) {
        return cardRepository.findListCardsBySetId(setId);
    }
}
