package com.deltadc.quizletclone.folderset;

import com.deltadc.quizletclone.folder.Folder;
import com.deltadc.quizletclone.set.Set;
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
@Table(name = "folder_set")
public class FolderSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_set_id")
    private Long folder_set_id;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "set_id", nullable = false)
    private Set set;

}
