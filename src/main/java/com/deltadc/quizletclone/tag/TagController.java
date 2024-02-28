package com.deltadc.quizletclone.tag;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    // Lấy ra tất cả tag
    @GetMapping("/tags")
    public ResponseEntity<?> getAllTags() {
        return tagService.getAllTags();
    }

    // Lấy ra tag theo id
    @GetMapping("/tags/{tag_id}")
    public ResponseEntity<?> getTag(@PathVariable("tag_id") Long tag_id) {
        return tagService.getTag(tag_id);
    }

    // Tạo tag mới
    @PostMapping("/create_tag")
    public ResponseEntity<?> createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    // Sửa tag
    @PutMapping("/tags/{tag_id}")
    public ResponseEntity<?> updateTag(@PathVariable("tag_id") Long tag_id, @RequestBody Tag tag) {
        return tagService.updateTag(tag_id, tag);
    }

    // Xóa tag
    @DeleteMapping("/tags/{tag_id}")
    public ResponseEntity<?> deleteTag(@PathVariable("tag_id") Long tag_id) {
        return tagService.deleteTag(tag_id);
    }
}
