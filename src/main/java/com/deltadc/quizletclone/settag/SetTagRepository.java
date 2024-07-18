package com.deltadc.quizletclone.settag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetTagRepository extends JpaRepository<SetTag, Long>, JpaSpecificationExecutor<SetTag> {

    @Query("SELECT st FROM SetTag st WHERE st.set_id = :setId")
    SetTag findBySetId(Long setId);
}
