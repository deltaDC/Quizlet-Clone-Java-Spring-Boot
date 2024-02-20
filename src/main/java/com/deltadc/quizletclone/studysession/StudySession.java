package com.deltadc.quizletclone.studysession;

import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.cardresponse.CardResponse;
import  com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Study_Session")
public class StudySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long session_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "set_id")
    private Set set;

    @OneToMany
    private List<Card> correct_cards;

    @OneToMany
    private List<Card> incorrect_cards;

    @OneToMany
    private List<Card> cards;

    @OneToMany
    private List<CardResponse> cardResponses;
}
