package com.deltadc.quizletclone.settag;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SetTagSpecification {

    public static Specification<SetTag> withDynamicQuery(Long setTagId, Long setId, Long tagId) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(setTagId)
                    .ifPresent(stid -> predicates.add(cb.equal(root.get("set_tag_id"), stid)));

            Optional.ofNullable(setId)
                    .ifPresent(sid -> predicates.add(cb.equal(root.get("set_id"), sid)));

            Optional.ofNullable(tagId)
                    .ifPresent(tid -> predicates.add(cb.equal(root.get("tag_id"), tid)));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
