package com.deltadc.quizletclone.settag;

import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.tag.Tag;
import com.deltadc.quizletclone.tag.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SetTagService {
    private final SetTagRepository setTagRepository;
    private final SetRepository setRepository;
    private final TagRepository tagRepository;

    public SetTag createSetTag(Long set_id, Long tag_id) {
        SetTag setTag = new SetTag();
        Set set = setRepository.findById(set_id).orElse(null);
        Tag tag = tagRepository.findById(tag_id).orElse(null);
        if (set == null || tag == null) {
            return null;
        }
        setTag.setSet(set);
        setTag.setTag(tag);
        set.setSetTag(setTag);
        tag.setSetTag(setTag);
        setTagRepository.save(setTag);
        return setTag;
    }

}
