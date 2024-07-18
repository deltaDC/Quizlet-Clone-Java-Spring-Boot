package com.deltadc.quizletclone.settag;

import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.tag.Tag;
import com.deltadc.quizletclone.tag.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SetTagService {
    private final SetTagRepository setTagRepository;
    private final TagRepository tagRepository;

    public SetTag createSetTag(SetTag setTag) {
        Long set_id = setTag.getSet_id();
        Long tag_id = setTag.getTag_id();

        SetTag newSetTag = new SetTag(set_id, tag_id);

        return setTagRepository.save(newSetTag);
    }

    public String deleteSetTag(Long set_tag_id) {
        setTagRepository.findById(set_tag_id).orElseThrow();

        setTagRepository.deleteById(set_tag_id);

        return "Deleted!";
    }

    public String getTagNameBySetId(Long setId) {
        SetTag setTag = setTagRepository.findBySetId(setId);

        Long tag_id = setTag.getTag_id();

        Tag t = tagRepository.findById(tag_id).orElseThrow();

        return t.getName();
    }

    public Page<SetTag> getSetTagsByFilter(int page, int size, Long setTagId, Long setId, Long tagId) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<SetTag> specification = SetTagSpecification.withDynamicQuery(setTagId, setId, tagId);

        return setTagRepository.findAll(specification, pageable);
    }

}
