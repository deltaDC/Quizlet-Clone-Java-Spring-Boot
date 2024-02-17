package com.deltadc.quizletclone.cardresponse;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CardResponseService {
    private final CardResponseRepository cardResponseRepository;
}
