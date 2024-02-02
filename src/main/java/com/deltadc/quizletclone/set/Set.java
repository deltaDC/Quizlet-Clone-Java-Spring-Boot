package com.deltadc.quizletclone.set;

import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.folder.Folder;
import com.deltadc.quizletclone.folderset.FolderSet;
import com.deltadc.quizletclone.review.Review;
import com.deltadc.quizletclone.settag.SetTag;
import com.deltadc.quizletclone.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "set")
public class Set {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Long set_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    private String description;

    private String createdAt;

    private String updatedAt;

    private boolean isPublic;

    @OneToMany(mappedBy = "set", cascade = CascadeType.ALL)
    private List<Card> cards;

    @ManyToMany(mappedBy = "set")
    private List<Folder> folders;

    @OneToMany(mappedBy = "set", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToOne(mappedBy = "set", cascade = CascadeType.ALL)
    private SetTag setTag;

    @OneToOne(mappedBy = "set")
    private List<FolderSet> folderSet;
}
