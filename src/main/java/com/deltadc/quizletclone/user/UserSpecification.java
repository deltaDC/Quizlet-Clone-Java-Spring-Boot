package com.deltadc.quizletclone.user;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSpecification {

    public static Specification<User> withDynamicQuery(String username, String email, String role) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Map<String, Object> params = new HashMap<>();

            params.put("username", username);
            params.put("email", email);
            params.put("role", role);

            params.forEach((key, value) -> {
                if (value != null) {
                    predicates.add(criteriaBuilder.equal(root.get(key), value));
                }
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
