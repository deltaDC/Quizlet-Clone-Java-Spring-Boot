package com.deltadc.quizletclone.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDTO {
    private Long card_id;

    private Long set_id;

    private String front_text;

    private String back_text;

    private String created_at;

    private String updated_at;

    private boolean is_known;

    public static CardDTO fromCardToCardDTO(Card card) {
        return CardDTO.builder()
                .card_id(card.getCard_id())
                .set_id(card.getSet().getSet_id())
                .front_text(card.getFront_text())
                .back_text(card.getBack_text())
                .created_at(card.getCreated_at())
                .updated_at(card.getUpdated_at())
                .is_known(card.is_known())
                .build();
    }
}
