package com.deltadc.quizletclone.folder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
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

    public static FolderDTO fromFolderToFolderDTO(Folder folder) {
        return FolderDTO.builder()
                .folderId(folder.getFolder_id())
                .title(folder.getTitle())
                .description(folder.getDescription())
                .createdAt(folder.getCreatedAt())
                .updatedAt(folder.getUpdatedAt())
                .isPublic(folder.isPublic())
                .build();
    }
}
