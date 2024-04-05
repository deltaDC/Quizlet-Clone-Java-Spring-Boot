package com.deltadc.quizletclone.set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetRepository extends JpaRepository<Set, Long> {
    @Query("SELECT s FROM Set s WHERE s.user.user_id = :userId")
    List<Set> findByUserId(Long userId);

    List<Set> findByIsPublic(boolean b);
}
