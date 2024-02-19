package com.deltadc.quizletclone.cardresponse;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardResponseService {
    private final CardResponseRepository cardResponseRepository;

    public ResponseEntity<?> getAllCardResponses() {
        return ResponseEntity.ok(cardResponseRepository.findAll());
    }

    public ResponseEntity<?> getCardResponseById(Long session_id, Long card_id, Long response_id) {
        return null;
    }
}
