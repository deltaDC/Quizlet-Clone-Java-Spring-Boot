package com.deltadc.quizletclone.cardresponse;

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

    // Lấy tất cả cardResponse trong 1 study session
    public ResponseEntity<?> getAllCardResponses(Long session_id) {
        StudySession studySession = studySessionRepository.findById(session_id).orElse(null);
        if (studySession == null) {
            return ResponseEntity.ok("This study session does not exist!");
        }
        return ResponseEntity.ok(studySession.getCardResponses());
    }

    // Lấy cardResponse theo response_id
    public ResponseEntity<?> getCardResponse(Long session_id, Long response_id) {
        StudySession studySession = studySessionRepository.findById(session_id).orElse(null);
        if (studySession == null) {
            return ResponseEntity.ok("Cannot find the StudySession");
        }
        List<CardResponse> cardResponses = studySession.getCardResponses();
        for (CardResponse cardResponse : cardResponses) {
            if (cardResponse.getResponse_id().equals(response_id)) {
                return ResponseEntity.ok(cardResponse);
            }
        }
        return ResponseEntity.ok("Cannot find CardResponse");
    }

    // Tạo 1 CardResponse
    public ResponseEntity<?> createCardResponse(Long session_id, CardResponse cardResponse) {
        StudySession studySession = studySessionRepository.findById(session_id).orElse(null);
        if (studySession == null) {
            return ResponseEntity.ok("Cannot find Study Session");
        }

        CardResponse newCardResponse = new CardResponse();
        newCardResponse.setStudySession(cardResponse.getStudySession());
        newCardResponse.set_known(cardResponse.is_known());
        newCardResponse.setCard(cardResponse.getCard());
        cardResponseRepository.save(newCardResponse);
        return ResponseEntity.ok("Created CardResponse!");
    }

    // Sửa 1 cardResponse
    public ResponseEntity<?> updateCardResponse(Long response_id, CardResponse cardResponse) {
        CardResponse existingCardResponse = cardResponseRepository.findById(response_id).orElse(null);
        if (existingCardResponse == null) {
            return ResponseEntity.ok("Cannot find CardResponse!");
        }

        existingCardResponse.setStudySession(cardResponse.getStudySession());
        existingCardResponse.set_known(cardResponse.is_known());
        existingCardResponse.setCard(cardResponse.getCard());
        cardResponseRepository.save(existingCardResponse);
        return ResponseEntity.ok("Updated CardResponse!");
    }

    public void deleteCardResponse(Long response_id) {
        cardResponseRepository.deleteById(response_id);
    }
}
