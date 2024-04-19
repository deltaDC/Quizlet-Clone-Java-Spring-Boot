package com.deltadc.quizletclone.card;

import com.deltadc.quizletclone.set.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "set_id", insertable = false, updatable = false)
    private Set set;

    @Column(name = "set_id", nullable = false)
    private Long set_id;

    @Column(name = "front_text", nullable = false)
    private String front_text;

    @Column(name = "back_text", nullable = false)
    private String back_text;

    private String created_at;

    private String updated_at;

    @Column(name = "is_known", nullable = false)
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
