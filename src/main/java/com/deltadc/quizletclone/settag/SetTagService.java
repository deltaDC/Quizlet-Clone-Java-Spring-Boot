package com.deltadc.quizletclone.settag;

import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.tag.Tag;
import com.deltadc.quizletclone.tag.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SetTagService {
    private final SetTagRepository setTagRepository;
    private final SetRepository setRepository;
    private final TagRepository tagRepository;

    public SetTag createSetTag(SetTag setTag) {
        Long set_id = setTag.getSet_id();
        Long tag_id = setTag.getTag_id();

        Set set = setRepository.findById(set_id).orElseThrow();
        Tag tag = tagRepository.findById(tag_id).orElseThrow();

        SetTag newSetTag = new SetTag(set.getSet_id(), tag.getTag_id());

        return setTagRepository.save(newSetTag);
    }

    public String deleteSetTag(Long set_tag_id) {
        setTagRepository.findById(set_tag_id).orElseThrow();

        setTagRepository.deleteById(set_tag_id);

        return "Deleted!";
    }

    public SetTag getSetTagBySetId(Long setId) {
        return setTagRepository.findBySetId(setId);
    }

    public List<SetTag> getAllSetTags() {
        return setTagRepository.findAll();
    }

    public String getTagNameBySetId(Long setId) {
        SetTag setTag = setTagRepository.findBySetId(setId);

        Long tag_id = setTag.getTag_id();

        Tag t = tagRepository.findById(tag_id).orElseThrow();

        return t.getName();
    }
}
