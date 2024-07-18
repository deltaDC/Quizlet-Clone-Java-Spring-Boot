package com.deltadc.quizletclone.settag;

import com.deltadc.quizletclone.response.ResponseObject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/set-tag")
public class SetTagController {
    private final SetTagService setTagService;

    //tao set tag moi
    @PostMapping("/create-set-tag")
    public ResponseEntity<ResponseObject> createSetTag(@RequestBody SetTag setTag) {
        SetTag createdSetTag = setTagService.createSetTag(setTag);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Successfully created set-tag")
                        .status(HttpStatus.OK)
                        .data(createdSetTag)
                        .build()
        );
    }

    //xoa set tag theo id
    @DeleteMapping("/{set_tag_id}")
    public ResponseEntity<ResponseObject> deleteSetTag(@PathVariable("set_tag_id") Long set_tag_id) {
        String response = setTagService.deleteSetTag(set_tag_id);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Successfully deleted set-tag")
                        .status(HttpStatus.OK)
                        .data(response)
                        .build()
        );
    }

    //lay ra tag name cua mot set
    @GetMapping("/set/{setId}/tag-name")
    public ResponseEntity<ResponseObject> getTagNameBySetId(@PathVariable("setId") Long setId) {
        String tagName = setTagService.getTagNameBySetId(setId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Successfully retrieved tag-name")
                        .status(HttpStatus.OK)
                        .data(tagName)
                        .build()
        );
    }

    //lay set tag theo filter
    @GetMapping("/list")
    public ResponseEntity<ResponseObject> getSetTagsByFilter(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "30") int size,
                                                             @Nullable @RequestParam("setTagId") Long setTagId,
                                                             @Nullable @RequestParam("setId") Long setId,
                                                             @Nullable @RequestParam("tagId") Long tagId) {
        Page<SetTag> setTags = setTagService.getSetTagsByFilter(page, size, setTagId, setId, tagId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Set-tags found")
                        .status(HttpStatus.OK)
                        .data(setTags)
                        .build()
        );
    }
}
