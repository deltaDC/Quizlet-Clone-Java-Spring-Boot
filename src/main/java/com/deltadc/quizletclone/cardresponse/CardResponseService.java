package com.deltadc.quizletclone.cardresponse;

import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.card.CardRepository;
import com.deltadc.quizletclone.studysession.StudySession;
import com.deltadc.quizletclone.studysession.StudySessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardResponseService {
    private final CardResponseRepository cardResponseRepository;
    private final StudySessionRepository studySessionRepository;
    private final CardRepository cardRepository;

    // Lấy tất cả cardResponse
    public ResponseEntity<?> getAllCardResponses() {
        return ResponseEntity.ok(cardResponseRepository.findAll());
    }

    // Lấy cardResponse theo card_id
    public ResponseEntity<?> getCardResponse(Long session_id, Long card_id) {
        StudySession studySession = studySessionRepository.findById(session_id).orElse(null);
        if (studySession == null) {
            return ResponseEntity.ok("Cannot find the StudySession");
        }
        List<Card> cards = studySession.getCards();
        for (Card card : cards) {
            if (card.getCard_id().equals(card_id)) {
                return ResponseEntity.ok(card.getCardResponse());
            }
        }
        return ResponseEntity.ok("Cannot find CardResponse");
    }

    // Tạo 1 CardResponse
    public ResponseEntity<?> createCardResponse(Long card_id, CardResponse cardResponse) {
        Card card = cardRepository.findById(card_id).orElse(null);
        if (card == null) {
            return ResponseEntity.ok("Cannot create CardResponse!");
        }
        CardResponse newCardResponse = new CardResponse();
        newCardResponse.setStudySession(cardResponse.getStudySession());
        newCardResponse.set_known(cardResponse.is_known());
        newCardResponse.setCard(cardResponse.getCard());
        cardResponseRepository.save(newCardResponse);
        return ResponseEntity.ok("Created CardResponse!");
    }

    // Sửa 1 cardResponse
//    public ResponseEntity<?> updateCardResponse(Long card_id, CardResponse cardResponse) {
//
//    }

    public void deleteCardResponse(Long response_id) {
        cardResponseRepository.deleteById(response_id);
    }
}
