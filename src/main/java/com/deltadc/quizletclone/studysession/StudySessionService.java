package com.deltadc.quizletclone.studysession;

import com.deltadc.quizletclone.card.Card;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StudySessionService {
    private final StudySessionRepository studySessionRepository;

    // Lấy ra tất cả StudySession
    public ResponseEntity<List<StudySession>> getAllStudySessions() {
        return ResponseEntity.ok(studySessionRepository.findAll());
    }

    // Lấy StudySession theo ID
    public ResponseEntity<?> getStudySessionById(Long id) {
        StudySession studySession = studySessionRepository.findById(id).orElse(null);
        return ResponseEntity.ok(Objects.requireNonNullElse(studySession, "Not found!"));
    }

//    // Tìm card trong studySession
//    public ResponseEntity<?> getCardInStudySession(Long session_id, Long card_id) {
//        StudySession studySession = studySessionRepository.findById(session_id).orElse(null);
//        if (studySession == null) {
//            return ResponseEntity.ok("Cannot find StudySession");
//        }
//        List<Card> cards = studySession.getCards();
//        for (Card card : cards) {
//            if (card.getCard_id().equals(card_id)) {
//                return ResponseEntity.ok(card);
//            }
//        }
//        return ResponseEntity.ok("Cannot find card");
//    }

    // Tạo mới 1 StudySession
    public ResponseEntity<String> createStudySession(StudySession studySession) {
        StudySession newStudySession = new StudySession();
        newStudySession.setUser(studySession.getUser());
        newStudySession.setSet(studySession.getSet());
        newStudySession.setCorrect_cards(studySession.getCorrect_cards());
        newStudySession.setIncorrect_cards(studySession.getIncorrect_cards());
        studySessionRepository.save(newStudySession);
        return ResponseEntity.ok("Created StudySession!");
    }

    // Update 1 StudySession
    public ResponseEntity<?> updateStudySession(Long id, StudySession studySession) {
        StudySession existingStudySession = studySessionRepository.findById(id).orElse(null);
        if (existingStudySession != null) {
            existingStudySession.setUser(studySession.getUser());
            existingStudySession.setSet(studySession.getSet());
            existingStudySession.setCorrect_cards(studySession.getCorrect_cards());
            existingStudySession.setIncorrect_cards(studySession.getIncorrect_cards());
            studySessionRepository.save(existingStudySession);
            return ResponseEntity.ok("Updated StudySession");
        } else {
            return ResponseEntity.ok("Cannot find the StudySession");
        }
    }

    // Xóa 1 StudySession
    public void deleteStudySession(Long id) {
        studySessionRepository.deleteById(id);
    }
}
