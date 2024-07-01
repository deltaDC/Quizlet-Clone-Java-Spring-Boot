package com.deltadc.quizletclone.set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetDTO {
    private Long setId;

    private Long userId;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
    private boolean isPublic;

    private String ownerName;

    private Long termCount;

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
    }

    public static SetDTO fromSetToSetDTO(Set set, String ownerName, long termCount) {
        return SetDTO.builder()
                .setId(set.getSet_id())
                .userId(set.getUser_id())
                .title(set.getTitle())
                .description(set.getDescription())
                .createdAt(set.getCreatedAt())
                .updatedAt(set.getUpdatedAt())
                .isPublic(set.isPublic())
                .ownerName(ownerName)
                .termCount(termCount)
                .build();
    }

}
