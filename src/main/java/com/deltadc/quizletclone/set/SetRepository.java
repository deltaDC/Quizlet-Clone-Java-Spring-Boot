package com.deltadc.quizletclone.set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SetRepository extends JpaRepository<Set, Long> {
    @Query("SELECT s FROM Set s WHERE s.user.user_id = :userId")
    List<Set> findByUserId(Long userId);
}
