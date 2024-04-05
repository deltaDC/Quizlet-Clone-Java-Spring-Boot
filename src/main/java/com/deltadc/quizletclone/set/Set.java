package com.deltadc.quizletclone.set;

import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.folderset.FolderSet;
import com.deltadc.quizletclone.review.Review;
import com.deltadc.quizletclone.settag.SetTag;
import com.deltadc.quizletclone.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sets")
public class Set {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Long set_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    private Long user_id;

    private String title;

    private String description;

    private String createdAt;

    private String updatedAt;

    private boolean isPublic;

    @JsonIgnore
    @OneToMany(mappedBy = "set", cascade = CascadeType.ALL)
    private List<Card> cards;

//    @ManyToMany(mappedBy = "set")
//    private List<Folder> folders;

    @JsonIgnore
    @OneToMany(mappedBy = "set", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @JsonIgnore
    @OneToOne(mappedBy = "set", cascade = CascadeType.ALL)
    private SetTag setTag;

    @JsonIgnore
    @OneToMany(mappedBy = "set", cascade = CascadeType.ALL)
    private List<FolderSet> folderSets;

//    public void addCard(Card card) {
//        cards.add(card);
//        card.setSet(this);
//    }
//
//    public void removeCard(Card card) {
//        cards.remove(card);
//        card.setSet(null);
//    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now().toString();
    }
}
