package com.deltadc.quizletclone.review;

import com.deltadc.quizletclone.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    private boolean isInvalidReview(Review review) {
        return  review.getTotalStars() > 5
                || review.getTotalStars() < 0;
    }

    //trả về tất cả review của một set
    @GetMapping("/sets/{set_id}/reviews")
    public ResponseEntity<Double> getSetAverageRating(@PathVariable("set_id") Long setId) {
        return reviewService.getSetAverageRating(setId);
    }

    // tạo một review mới
    @PostMapping("/sets/{set_id}/review")
    public ResponseEntity<ResponseObject> postSetReviews(@PathVariable("set_id") Long setId,
                                                         @NonNull @RequestBody Review review) {
        if(isInvalidReview(review)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                    .message("Review is invalid")
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .build()
            );
        }

        Review createdReview = reviewService.postSetReviews(setId, review);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Review is invalid")
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .data(createdReview)
                        .build()
        );
    }

    //sua mot review theo id
    @PutMapping("/edit/{reviewId}")
    public ResponseEntity<ResponseObject> editReviewById(@PathVariable("reviewId") Long reviewId,
                                                         @NonNull @RequestBody Review newReview) {
        if(isInvalidReview(newReview)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Review is invalid")
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .build()
            );
        }

        Review updatedReview = reviewService.editReviewById(reviewId, newReview);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Review updated")
                        .status(HttpStatus.OK)
                        .data(updatedReview)
                        .build()
        );
    }

    //xoa mot review theo id
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<ResponseObject> deleteReviewById(@PathVariable("reviewId") Long reviewId) {
        String response = reviewService.deleteReviewById(reviewId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(response)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    //lay review theo filter
    @GetMapping("/list")
    public ResponseEntity<ResponseObject> getReviewsByFilter(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "30") int size,
                                                             @Nullable @RequestParam("totalStars") Integer totalStars,
                                                             @Nullable @RequestParam("userId") Long userId,
                                                             @Nullable @RequestParam("setId") Long setId) {
        Page<Review> reviews = reviewService.getReviewsByFilter(page, size, totalStars, userId, setId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("reviews found")
                        .status(HttpStatus.OK)
                        .data(reviews)
                        .build()
        );
    }

}
