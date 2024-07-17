package com.deltadc.quizletclone.folder;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FolderSpecification {

    public static Specification<Folder> withDynamicQuery(String title, Boolean isPublic, Long userId) {
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
