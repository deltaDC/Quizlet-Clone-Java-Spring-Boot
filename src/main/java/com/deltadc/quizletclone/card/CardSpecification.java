package com.deltadc.quizletclone.card;

import com.deltadc.quizletclone.set.Set;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardSpecification {

    public static Specification<Card> withDynamicQuery(Long cardId, Long setId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(setId)
                    .ifPresent(sid -> predicates.add(criteriaBuilder.equal(root.get("set_id"), sid)));

            Optional.ofNullable(cardId)
                    .ifPresent(cid -> predicates.add(criteriaBuilder.equal(root.get("card_id"), cid)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
