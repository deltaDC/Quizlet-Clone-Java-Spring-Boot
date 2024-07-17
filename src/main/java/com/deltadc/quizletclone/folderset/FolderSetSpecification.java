package com.deltadc.quizletclone.folderset;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FolderSetSpecification {

    public static Specification<FolderSet> withDynamicQuery(Long folderSetId, Long folderId, Long setId) {
        return (root, query, criteriaBuilder) -> {
                List <Predicate> predicates = new ArrayList<>();

                Optional.ofNullable(folderSetId)
                        .ifPresent(t -> predicates.add(criteriaBuilder.equal(root.get("folder_set_id"), folderSetId)));

                Optional.ofNullable(folderId)
                        .ifPresent(ip -> predicates.add(criteriaBuilder.equal(root.get("folder_id"), folderId)));

                Optional.ofNullable(setId)
                        .ifPresent(uid -> predicates.add(criteriaBuilder.equal(root.get("set_id"), setId)));

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
