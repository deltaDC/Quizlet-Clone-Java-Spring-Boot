package com.deltadc.quizletclone.cardresponse;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CardResponseController {
    private final CardResponseService cardResponseService;

    @GetMapping("/study_sessions/{session_id}/cards/{card_id}/responses")
    public List<CardResponse> getAllCardResponses() {
        return cardResponseService.getAllCardResponses();
    }

    @GetMapping("/study_sessions/{session_id}/cards/{card_id}/responses/response_id")
    public CardResponse getCardResponseById(@PathVariable("session_id") Long session_id, @PathVariable("card_id") Long card_id,
                                            @PathVariable("response_id") Long response_id) {
        return null;
    }
}
