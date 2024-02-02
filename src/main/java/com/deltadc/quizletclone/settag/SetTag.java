package com.deltadc.quizletclone.settag;

import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.tag.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "set_tags")
public class SetTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_tag_id")
    private Long set_tag_id;

    @OneToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @OneToOne
    @JoinColumn(name = "set_id", nullable = false, unique = true)
    private Set set;


}
