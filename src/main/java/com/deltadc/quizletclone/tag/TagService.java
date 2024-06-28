package com.deltadc.quizletclone.tag;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTag(Long tag_id) {
        return tagRepository.findById(tag_id).orElseThrow();
    }

    public Tag createTag(Tag newTag) {
        Tag tag = Tag.builder()
                .name(newTag.getName())
                .build();
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long tag_id, Tag newTag) {
        Tag tag = tagRepository.findById(tag_id).orElseThrow();
        tag.setName(newTag.getName());
        tagRepository.save(tag);
        return tag;
    }

    public String deleteTag(Long tag_id) {
        tagRepository.findById(tag_id).orElseThrow(null);
        tagRepository.deleteById(tag_id);
        return "Deleted!";
    }
}
