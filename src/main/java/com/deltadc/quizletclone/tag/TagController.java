package com.deltadc.quizletclone.tag;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/tags/{tag_id}")
    public ResponseEntity<?> getTag(@PathVariable("tag_id") Long tag_id) {
        return tagService.getTag(tag_id);
    }

    @PostMapping("sets/{set_id}/create_tag")
    public ResponseEntity<?> createTag(@PathVariable Long set_id ,@RequestBody Tag tag) {
        return tagService.createTag(set_id, tag);
    }

    @PutMapping("/tags/{tag_id}")
    public ResponseEntity<?> updateTag(@PathVariable("tag_id") Long tag_id, @RequestBody Tag tag) {
        return tagService.updateTag(tag_id, tag);
    }

    @DeleteMapping("tags/{tag_id}")
    public ResponseEntity<?> deleteTag(@PathVariable("tag_id") Long tag_id) {
        return tagService.deleteTag(tag_id);
    }
}