package com.deltadc.quizletclone.tag;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagSpecification {

    public static Specification<Tag> withDynamicQuery(Long tagId, String tagName) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(tagId)
                    .ifPresent(tid -> predicates.add(criteriaBuilder.equal(root.get("tag_id"), tid)));

            Optional.ofNullable(tagName)
                    .ifPresent(tn -> predicates.add(criteriaBuilder.equal(root.get("tag_name"), tn)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
