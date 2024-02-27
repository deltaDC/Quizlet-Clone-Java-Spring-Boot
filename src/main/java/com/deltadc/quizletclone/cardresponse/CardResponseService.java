package com.deltadc.quizletclone.cardresponse;

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
            return ResponseEntity.badRequest().body("Cannot find study session");
        }
        return ResponseEntity.ok(studySession.getCardResponses());
    }

    // Lấy cardResponse theo response_id
    public ResponseEntity<?> getCardResponse(Long response_id) {
        CardResponse cardResponse = cardResponseRepository.findById(response_id).orElse(null);
        if (cardResponse == null) {
            return ResponseEntity.badRequest().body("Cannot find card response");
        }
        return ResponseEntity.ok(cardResponse);
    }

    // Tạo 1 CardResponse
    public ResponseEntity<?> createCardResponse(Long session_id, CardResponse cardResponse) {
        StudySession studySession = studySessionRepository.findById(session_id).orElse(null);
        if (studySession == null) {
            return ResponseEntity.badRequest().body("Cannot find study session");
        }

        CardResponse newCardResponse = new CardResponse();
        newCardResponse.setStudySession(cardResponse.getStudySession());
        newCardResponse.set_known(cardResponse.is_known());
        newCardResponse.setCard(cardResponse.getCard());
        cardResponseRepository.save(newCardResponse);
        return ResponseEntity.ok("Created card response!");
    }

    // Sửa 1 cardResponse
    public ResponseEntity<?> updateCardResponse(Long response_id, CardResponse cardResponse) {
        CardResponse existingCardResponse = cardResponseRepository.findById(response_id).orElse(null);
        if (existingCardResponse == null) {
            return ResponseEntity.badRequest().body("Cannot find card response!");
        }

        existingCardResponse.setStudySession(cardResponse.getStudySession());
        existingCardResponse.set_known(cardResponse.is_known());
        existingCardResponse.setCard(cardResponse.getCard());
        cardResponseRepository.save(existingCardResponse);
        return ResponseEntity.ok("Updated CardResponse!");
    }

    public ResponseEntity<?> deleteCardResponse(Long response_id) {
        CardResponse cardResponse = cardResponseRepository.findById(response_id).orElse(null);
        if (cardResponse == null) {
            return ResponseEntity.badRequest().build();
        }
        cardResponseRepository.deleteById(response_id);
        return ResponseEntity.ok("Deleted card response");
    }
}
