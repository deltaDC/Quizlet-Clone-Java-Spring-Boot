package com.deltadc.quizletclone.card;

import com.deltadc.quizletclone.cardresponse.CardResponse;
import com.deltadc.quizletclone.set.Set;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long card_id;

    @OneToOne
    @JoinColumn(name = "response_id")
    private CardResponse cardResponse;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private Set set;

    private String front_text;

    private String back_text;

    private String created_at;

    private String updated_at;
}
