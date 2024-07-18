package com.deltadc.quizletclone.tag;

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
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    //lay tag theo filter
    @GetMapping("/list")
    public ResponseEntity<ResponseObject> getTagsByFilter(@RequestParam(defaultValue = "0") int page,
                                                          @Nullable @RequestParam(defaultValue = "30") int size,
                                                          @Nullable @RequestParam("tagId") Long tagId,
                                                          @Nullable @RequestParam("tagName") String tagName) {
        Page<Tag> tags = tagService.getTagsByFilter(page, size, tagId, tagName);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tags found")
                        .status(HttpStatus.OK)
                        .data(tags)
                        .build()
        );
    }

    // Tạo tag mới
    @PostMapping("/create_tag")
    public ResponseEntity<ResponseObject> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.createTag(tag);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tag created successfully")
                        .status(HttpStatus.CREATED)
                        .data(createdTag)
                        .build()
        );
    }

    // Sửa tag
    @PutMapping("/{tag_id}")
    public ResponseEntity<ResponseObject> updateTag(@PathVariable("tag_id") Long tag_id,
                                                    @RequestBody Tag tag) {
        Tag updatedTag = tagService.updateTag(tag_id, tag);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tag fetched successfully")
                        .status(HttpStatus.OK)
                        .data(updatedTag)
                        .build()
        );
    }

    // Xóa tag
    @DeleteMapping("/{tag_id}")
    public ResponseEntity<ResponseObject> deleteTag(@PathVariable("tag_id") Long tag_id) {
        String response = tagService.deleteTag(tag_id);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(response)
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
