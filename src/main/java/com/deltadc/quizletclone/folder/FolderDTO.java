package com.deltadc.quizletclone.folder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FolderDTO {

    private Long folderId;

    private String title;

    private String description;

    private String createdAt;

    private String updatedAt;

    private boolean isPublic;

    public FolderDTO(Long folderId, String title, String description, String createdAt, String updatedAt, boolean isPublic) {
        this.folderId = folderId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isPublic = isPublic;
    }
}
