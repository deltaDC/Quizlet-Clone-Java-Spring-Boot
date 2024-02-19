package com.deltadc.quizletclone.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    //trả về tất cả review của một set
    @GetMapping("/sets/{set_id}/reviews")
    public ResponseEntity<Double> getSetAverageRating(@PathVariable("set_id") Long setId) {
        return reviewService.getSetAverageRating(setId);
    }


    // tạo một review mới
    @PostMapping("/sets/{set_id}/review")
    public ResponseEntity<String> postSetReviews(@PathVariable("set_id") Long setId, @RequestBody String json) {
        return reviewService.postSetReviews(setId, json);
    }
}
