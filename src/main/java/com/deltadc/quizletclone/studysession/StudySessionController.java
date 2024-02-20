package com.deltadc.quizletclone.studysession;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class StudySessionController {
    private final StudySessionService studySessionService;

    // Lấy ra toàn bộ studySession
    @GetMapping("/study_sessions")
    public ResponseEntity<?> getAllStudySessions() {
        return studySessionService.getAllStudySessions();
    }

    @GetMapping("/study_sessions/{session_id}")
    public ResponseEntity<?> getStudySessionById(@PathVariable("session_id") Long id) {
        return studySessionService.getStudySessionById(id);
    }

    @PostMapping("/study_sessions")
    public ResponseEntity<String> createStudySession(@RequestBody StudySession newStudySession) {
        return studySessionService.createStudySession(newStudySession);
    }

    @PutMapping("/study_sessions/{session_id}")
    public ResponseEntity<?> updateStudySession(@PathVariable("session_id") Long session_id, StudySession studySession) {
        return studySessionService.updateStudySession(session_id, studySession);
    }

    @DeleteMapping("/study_session/{session_id}")
    public ResponseEntity<?> deleteStudySession(@PathVariable("session_id") Long id) {
        studySessionService.deleteStudySession(id);
        return ResponseEntity.ok("Deleted completely!");
    }
}
