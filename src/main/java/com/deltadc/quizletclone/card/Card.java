package com.deltadc.quizletclone.card;

import com.deltadc.quizletclone.set.Set;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "set_id")
    private Set set;

    private String front_text;

    private String back_text;

    private String created_at;

    private String updated_at;

    private boolean is_known;

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = LocalDateTime.now().toString();
    }
}
