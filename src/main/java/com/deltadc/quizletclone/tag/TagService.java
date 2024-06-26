package com.deltadc.quizletclone.tag;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    // Lấy ra tất cả tag
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    // Lấy ra 1 tag
    public Tag getTag(Long tag_id) {
        return tagRepository.findById(tag_id).orElse(null);
    }

    public Tag createTag(Tag newTag) {
        Tag tag = new Tag();
        tag.setName(newTag.getName());
        tagRepository.save(newTag);
        return tag;
    }

    // Update 1 tag
    public Tag updateTag(Long tag_id, Tag newTag) {
        Tag tag = tagRepository.findById(tag_id).orElse(null);
        if (tag == null) {
            return null;
        }
        tag.setName(newTag.getName());
        tagRepository.save(tag);
        return tag;
    }

    // Xóa 1 tag
    public String deleteTag(Long tag_id) {
        Tag tag = tagRepository.findById(tag_id).orElse(null);
        if (tag == null) {
            return "Cannot find this tag!";
        }
        tagRepository.deleteById(tag_id);
        return "Deleted!";
    }
}
