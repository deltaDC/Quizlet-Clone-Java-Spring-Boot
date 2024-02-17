package com.deltadc.quizletclone.cardresponse;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CardResponseController {
    private final CardResponseService cardResponseService;
}
