package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.folder.Folder;
import com.deltadc.quizletclone.review.Review;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.studysession.StudySession;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Set> sets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Folder> folders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private StudySession studySession;


}
