package com.deltadc.quizletclone.cardresponse;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardResponseService {
    private final CardResponseRepository cardResponseRepository;

    public List<CardResponse> getAllCardResponses() {
        return cardResponseRepository.findAll();
    }

    public CardResponse getCardResponseById(Long session_id, Long card_id, Long response_id) {
        return null;
    }
}
