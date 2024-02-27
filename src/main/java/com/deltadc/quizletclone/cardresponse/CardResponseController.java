package com.deltadc.quizletclone.cardresponse;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CardResponseController {
    private final CardResponseService cardResponseService;

    @GetMapping("/study_sessions/{session_id}/responses")
    public ResponseEntity<?> getAllCardResponses(@PathVariable("session_id") Long session_id) {
        return cardResponseService.getAllCardResponses(session_id);
    }

    @GetMapping("/study_sessions/{session_id}/responses/{response_id}")
    public ResponseEntity<?> getCardResponseById(@PathVariable("session_id") Long session_id, @PathVariable("response_id") Long response_id) {
        return cardResponseService.getCardResponse(response_id);
    }

    @PostMapping("/study_sessions/{session_id}/responses")
    public ResponseEntity<?> createCardResponse(@PathVariable("session_id") Long session_id, @RequestBody CardResponse cardResponse) {
        return cardResponseService.createCardResponse(session_id, cardResponse);
    }

    @PutMapping("/study_sessions/{session_id}/responses/{response_id}")
    public ResponseEntity<?> updateCardResponse(@PathVariable("session_id") Long session_id, @PathVariable("response_id") Long response_id, @RequestBody CardResponse cardResponse) {
        return cardResponseService.createCardResponse(response_id, cardResponse);
    }

    @DeleteMapping("/study_sessions/{session_id}/responses/{response_id}/")
    public ResponseEntity<?> deleteCardResponse(@PathVariable("session_id") Long session_id, @PathVariable("response_id") Long response_id) {
        cardResponseService.deleteCardResponse(response_id);
        return ResponseEntity.ok("Deleted completely!");
    }
}
