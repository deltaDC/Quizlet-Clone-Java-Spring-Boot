package com.deltadc.quizletclone.tag;

import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.settag.SetTag;
import com.deltadc.quizletclone.settag.SetTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final SetRepository setRepository;
    private final SetTagRepository setTagRepository;

    public ResponseEntity<?> getTag(Long tag_id) {
        Tag tag = tagRepository.findById(tag_id).orElse(null);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Cannot find this tag");
        }
        return ResponseEntity.ok(tag);
    }

    public ResponseEntity<?> createTag(Long set_id, Tag tag) {
        Set set = setRepository.findById(set_id).orElse(null);
        if (set == null) {
            return ResponseEntity.badRequest().body("Cannot find this set!");
        }
        Tag newTag = new Tag();
        SetTag setTag = new SetTag();
        setTag.setSet(set);
        setTag.setTag(newTag);
        newTag.setSetTag(setTag);
        set.setSetTag(setTag);
        return ResponseEntity.ok("Created!");
    }

    public ResponseEntity<?> updateTag(Long tag_id, Tag update_tag) {
        Tag tag = tagRepository.findById(tag_id).orElse(null);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Cannot find this tag!");
        }
        tag.setName(update_tag.getName());
        tagRepository.save(tag);
        return ResponseEntity.ok("Updated!");
    }

    public ResponseEntity<?> deleteTag(Long tag_id) {
        Tag tag = tagRepository.findById(tag_id).orElse(null);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Cannot find this tag!");
        }
        SetTag setTag = tag.getSetTag();
        setTagRepository.deleteById(setTag.getSet_tag_id());
        tagRepository.deleteById(tag_id);
        return ResponseEntity.ok("Deleted!");
    }
}