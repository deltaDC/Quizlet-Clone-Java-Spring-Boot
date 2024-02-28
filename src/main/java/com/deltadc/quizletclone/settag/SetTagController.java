package com.deltadc.quizletclone.settag;

import com.deltadc.quizletclone.set.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SetTagController {
    private final SetTagService setTagService;

    @PostMapping("/create_set_tag")
    public ResponseEntity<?> createSetTag(@RequestParam Long set_id, @RequestParam Long tag_id) {
        return setTagService.createSetTag(set_id, tag_id);
    }

    @DeleteMapping("/set_tags/{set_tag_id}")
    public ResponseEntity<?> deleteSetTag(@PathVariable("set_tag_id") Long set_tag_id) {
        return setTagService.deleteSetTag(set_tag_id);
    }
}
