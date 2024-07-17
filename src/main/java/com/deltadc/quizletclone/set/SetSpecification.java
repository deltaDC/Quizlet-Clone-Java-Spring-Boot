package com.deltadc.quizletclone.set;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public class SetSpecification {

    public static Specification<Set> withDynamicQuery(String title, Boolean isPublic, Long userId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(title)
                    .ifPresent(t -> predicates.add(criteriaBuilder.like(root.get("title"), "%" + t + "%")));

            Optional.ofNullable(isPublic)
                    .ifPresent(ip -> predicates.add(criteriaBuilder.equal(root.get("isPublic"), ip)));

            Optional.ofNullable(userId)
                    .ifPresent(uid -> predicates.add(criteriaBuilder.equal(root.get("user_id"), uid)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
