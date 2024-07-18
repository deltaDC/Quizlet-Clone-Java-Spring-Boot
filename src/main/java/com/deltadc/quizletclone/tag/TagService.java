package com.deltadc.quizletclone.tag;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Tag createTag(Tag newTag) {
        Tag tag = Tag.builder()
                .name(newTag.getName())
                .build();
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long tag_id, Tag newTag) {
        return tagRepository.findById(tag_id)
                .map(tag -> {
                    tag.setName(newTag.getName());
                    return tagRepository.save(tag);
                })
                .orElseThrow();
    }

    public String deleteTag(Long tag_id) {
        return tagRepository.findById(tag_id)
                .map(tag -> {
                    tagRepository.deleteById(tag_id);
                    return "Deleted!";
                })
                .orElse(null);
    }

    public Page<Tag> getTagsByFilter(int page, int size, Long tagId, String tagName) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Tag> specification = TagSpecification.withDynamicQuery(tagId, tagName);

        return tagRepository.findAll(specification, pageable);
    }
}
