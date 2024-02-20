package com.deltadc.quizletclone.cardresponse;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CardResponseController {
    private final CardResponseService cardResponseService;

    @GetMapping("/study_sessions/{session_id}/cards/{card_id}/responses")
    public ResponseEntity<?> getAllCardResponses() {
        return cardResponseService.getAllCardResponses();
    }

    @GetMapping("/study_sessions/{session_id}/cards/{card_id}/responses")
    public ResponseEntity<?> getCardResponseById(@PathVariable("session_id") Long session_id, @PathVariable("card_id") Long card_id) {
        return cardResponseService.getCardResponse(session_id, card_id);
    }
}
