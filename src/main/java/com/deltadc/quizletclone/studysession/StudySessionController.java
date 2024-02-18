package com.deltadc.quizletclone.studysession;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class StudySessionController {
    private final StudySessionService studySessionService;

    // Lấy ra toàn bộ studySession
    @GetMapping("/study_sessions")
    public List<StudySession> getAllStudySessions() {
        return studySessionService.getAllStudySessions();
    }

    @GetMapping("/study_sessions/{session_id}")
    public StudySession getStudySessionById(@PathVariable("session_id") Long id) {
        return studySessionService.getStudySessionById(id);
    }

    @PostMapping("/study_sessions")
    public StudySession createStudySession(StudySession newStudySession) {
        return studySessionService.createStudySession(newStudySession);
    }

    @PutMapping
    public StudySession updateStudySession() {
        return null;
    }

    @DeleteMapping("/study_session/{session_id}")
    public void deleteStudySession(@PathVariable("session_id") Long id) {
        studySessionService.deleteStudySession(id);
    }
}
