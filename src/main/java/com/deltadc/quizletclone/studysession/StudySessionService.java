package com.deltadc.quizletclone.studysession;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class StudySessionService {
    private final StudySessionRepository studySessionRepository;

    public List<StudySession> getAllStudySessions() {
        return studySessionRepository.findAll();
    }

    public StudySession getStudySessionById(Long id) {
        return studySessionRepository.findById(id).orElse(null);
    }

    public StudySession createStudySessions(StudySession studySession) {
        return null;
    }
}
