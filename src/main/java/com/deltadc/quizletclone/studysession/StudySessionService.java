package com.deltadc.quizletclone.studysession;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudySessionService {
    private final StudySessionRepository studySessionRepository;

    // Lấy ra tất cả StudySession
    public List<StudySession> getAllStudySessions() {
        return studySessionRepository.findAll();
    }

    // Lấy StudySession theo ID
    public StudySession getStudySessionById(Long id) {
        return studySessionRepository.findById(id).orElse(null);
    }

    // Tạo mới 1 StudySession
    public StudySession createStudySession(StudySession studySession) {
        StudySession newStudySession = new StudySession();
        newStudySession.setUser(studySession.getUser());
        newStudySession.setSet(studySession.getSet());
        newStudySession.setCorrect_cards(studySession.getCorrect_cards());
        newStudySession.setIncorrect_cards(studySession.getIncorrect_cards());
        return studySessionRepository.save(newStudySession);
    }

    // Update 1 StudySession
    public StudySession updateStudySession(Long id, StudySession studySession) {
        StudySession existingStudySession = getStudySessionById(id);
        if (existingStudySession != null) {
            existingStudySession.setUser(studySession.getUser());
            existingStudySession.setSet(studySession.getSet());
            existingStudySession.setCorrect_cards(studySession.getCorrect_cards());
            existingStudySession.setIncorrect_cards(studySession.getIncorrect_cards());
            return studySessionRepository.save(existingStudySession);
        } else {
            return null;
        }
    }

    // Xóa 1 StudySession
    public void deleteStudySession(Long id) {
        studySessionRepository.deleteById(id);
    }
}
