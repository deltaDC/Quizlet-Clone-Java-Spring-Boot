package com.deltadc.quizletclone.card;

import  com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final SetRepository setRepository;
    private final UserRepository userRepository;


    // Các phương thức xử lý Card
    public List<CardDTO> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        List<CardDTO> cardDTOS = new ArrayList<>();
        for (Card card : cards) {
            cardDTOS.add(new CardDTO(card));
        }
        return cardDTOS;
    }

    public CardDTO getCardById(Long id) {
        Card card = cardRepository.findById(id).orElseThrow();
        return new CardDTO(card);
    }

    public Page<CardDTO> getCardsInSet(Long set_id, int page, int size) {
        Set set = setRepository.findById(set_id).orElseThrow();
        Page<Card> cards = cardRepository.findCardsBySetId(set_id, PageRequest.of(page, size));
        return cards.map(CardDTO::new);
    }

    public Card createCard(Card card, Long set_id) throws Exception {
        Set set = setRepository.findById(set_id).orElseThrow();

        if(card.getFront_text().isEmpty() || card.getBack_text().isEmpty()) {
            throw new Exception("card should not be empty");
        }

        Card newCard = new Card();

        newCard.set_known(false);
        newCard.setFront_text(card.getFront_text());
        newCard.setBack_text(card.getBack_text());
        newCard.setSet_id(set.getSet_id());
        cardRepository.save(newCard);
        return newCard;
    }

    public CardDTO updateCard(Long id, Card updatedCard) {

        Card existingCard = cardRepository.findById(id).orElseThrow();

        existingCard.setFront_text(updatedCard.getFront_text());
        existingCard.setBack_text(updatedCard.getBack_text());
        existingCard.onUpdate();
        cardRepository.save(existingCard);

        return new CardDTO(existingCard);

    }

    public void deleteCard(Long id) {

        Card card = cardRepository.findById(id).orElseThrow();
        cardRepository.deleteById(id);
    }

    public List<Card> createCards(Long setId, List<Card> cardList) throws Exception {

        Optional<Set> set = setRepository.findById(setId);

        if(set.isPresent()) {
            cardList.forEach(card -> card.setSet_id(setId));

            return cardRepository.saveAll(cardList);
        } else {
            throw new Exception("set is not found");
        }

    }
}
