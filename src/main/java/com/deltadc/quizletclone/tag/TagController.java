package com.deltadc.quizletclone.tag;

import com.deltadc.quizletclone.response.ResponseObject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tag")
public class TagController {
    private final TagService tagService;

    // Lấy ra tất cả tag
    @GetMapping("/get-all-tags")
    public ResponseEntity<ResponseObject> getAllTags() {
        List<Tag> tags = tagService.getAllTags();

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("All tags fetched successfully")
                        .status(HttpStatus.OK)
                        .data(tags)
                        .build()
        );
    }

    // Lấy ra tag theo id
    @GetMapping("/{tag_id}")
    public ResponseEntity<ResponseObject> getTag(@PathVariable("tag_id") Long tag_id) {
        Tag tag = tagService.getTag(tag_id);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tag fetched successfully")
                        .status(HttpStatus.OK)
                        .data(tag)
                        .build()
        );
    }

    // Tạo tag mới
    @PostMapping("/create_tag")
    public ResponseEntity<?> createTag(@RequestBody Tag tag) {
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
    public ResponseEntity<?> deleteTag(@PathVariable("tag_id") Long tag_id) {
        String response = tagService.deleteTag(tag_id);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(response)
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
