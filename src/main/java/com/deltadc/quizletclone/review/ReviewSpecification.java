package com.deltadc.quizletclone.review;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewSpecification {

    public static Specification<Review> withDynamicQuery(Integer totalStars, Long setId, Long userId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(totalStars)
                    .ifPresent(t -> predicates.add(criteriaBuilder.equal(root.get("totalStars"), totalStars)));

            Optional.ofNullable(setId)
                    .ifPresent(t -> predicates.add(criteriaBuilder.equal(root.get("set_id"), setId)));

            Optional.ofNullable(userId)
                    .ifPresent(uid -> predicates.add(criteriaBuilder.equal(root.get("user_id"), userId)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
