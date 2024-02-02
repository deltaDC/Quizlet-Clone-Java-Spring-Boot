package com.deltadc.quizletclone.cardresponse;

import com.deltadc.quizletclone.studysession.StudySession;
import com.deltadc.quizletclone.card.Card;
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
@Table(name = "CardResponse")
public class CardResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long response_id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private StudySession studySession;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private boolean is_known;
}
