package com.deltadc.quizletclone.settag;

import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.tag.Tag;
import com.deltadc.quizletclone.tag.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SetTagService {
    private final SetTagRepository setTagRepository;
    private final SetRepository setRepository;
    private final TagRepository tagRepository;

    public ResponseEntity<?> createSetTag(Long set_id, Long tag_id) {
        Set set = setRepository.findById(set_id).orElse(null);
        if (set == null) {
            return ResponseEntity.badRequest().body("Cannot create set tag because there is not set!");
        }
        Tag tag = tagRepository.findById(tag_id).orElse(null);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Cannot create set tag because there is not tag!");
        }
        SetTag setTag = new SetTag();
        setTag.setSet(set);
        setTag.setTag(tag);
        setTagRepository.save(setTag);
        return ResponseEntity.ok("Created!");
    }

    public ResponseEntity<?> deleteSetTag(Long set_tag_id) {
        SetTag setTag = setTagRepository.findById(set_tag_id).orElse(null);
        if (setTag == null) {
            return ResponseEntity.badRequest().body("Cannot find this set tag!");
        }
        setTagRepository.deleteById(set_tag_id);
        return ResponseEntity.ok("Deleted!");
    }
}
