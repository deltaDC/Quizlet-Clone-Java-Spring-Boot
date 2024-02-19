package com.deltadc.quizletclone.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.set.set_id = :setId")
    List<Review> findReviewsBySetId(Long setId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r WHERE r.set.set_id = :setId AND r.user.user_id = :userId")
    boolean existsBySetIdAndUserId(Long setId, Long userId);
}
