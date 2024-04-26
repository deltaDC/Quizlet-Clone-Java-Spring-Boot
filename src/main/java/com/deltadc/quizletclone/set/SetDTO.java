package com.deltadc.quizletclone.set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetDTO {
    private Long setId;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
    private boolean isPublic;

    private String ownerName;

    private Long totalTerms;

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
    }

}
