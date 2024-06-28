package com.deltadc.quizletclone.review;

import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final SetRepository setRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private boolean isReviewOwner(Review r) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByEmail(username).orElseThrow();
        Long userId = user.getUser_id();

        return Objects.equals(userId, r.getUser_id());
    }

    public ResponseEntity<Double> getSetAverageRating(@PathVariable Long setId) {
        List<Review> setReviews = reviewRepository.findReviewsBySetId(setId);

        int totalStars = setReviews.stream()
                .mapToInt(Review::getTotalStars)
                .sum();

        double averageRating = setReviews.isEmpty()
                ? 0.0
                : (double) totalStars / setReviews.size();

        return ResponseEntity.ok(averageRating);
    }


    public Review postSetReviews(@PathVariable Long setId, @RequestBody Review review) {
        Long userId = review.getUser_id();

        boolean hasReviewed = reviewRepository.existsBySetIdAndUserId(setId, userId);
        if (hasReviewed) {
            return null;
        }

        boolean isOwner = setRepository.findById(setId)
                .map(s -> s.getUser_id().equals(userId))
                .orElse(false);
        if(isOwner) {
            return null;
        }

        Review createdReview = Review.builder()
                .user_id(review.getUser_id())
                .set_id(review.getSet_id())
                .totalStars(review.getTotalStars())
                .build();

        return reviewRepository.save(createdReview);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Review editReviewById(Long reviewId, Review newReview) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        if(!isReviewOwner(review)) {
            return null;
        }

        review.setTotalStars(newReview.getTotalStars());

        return reviewRepository.save(review);
    }

    public String deleteReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        if(!isReviewOwner(review)) {
            return null;
        }

        reviewRepository.delete(review);

        return "Review delete";
    }

    public Review getReviewByUserIdAndSetId(Long setId, Long userId) {
        return reviewRepository.findBySetIdAndUserId(setId, userId);
    }
}
