package com.deltadc.quizletclone.studysession;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StudySessionController {
    private final StudySessionService studySessionService;
}
