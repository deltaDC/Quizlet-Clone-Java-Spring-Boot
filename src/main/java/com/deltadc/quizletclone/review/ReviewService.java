package com.deltadc.quizletclone.review;

import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final SetRepository setRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ResponseEntity<Double> getSetAverageRating(@PathVariable Long setId) {
        List<Review> setReviews = reviewRepository.findReviewsBySetId(setId);

        int totalStars = 0;
        int numberOfReviews = setReviews.size();

        for (Review review : setReviews) {
            totalStars += review.getTotalStars();
        }

        double averageRating = numberOfReviews > 0 ? (double) totalStars / numberOfReviews : 0.0;

        return ResponseEntity.ok(averageRating);
    }


    public ResponseEntity<String> postSetReviews(@PathVariable Long setId, @RequestBody String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            Integer total_stars = node.get("total_stars").asInt();

            // Trích xuất thông tin người dùng từ JWT
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();

            // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
            User user = userRepository.findByEmail(username).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại");
            }
            Long userId = user.getUser_id();

            // Tìm setId đó có tồn tại hay không
            Set s = setRepository.findById(setId).orElse(null);
            if (s == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SetId không tồn tại");
            }

            // Kiểm tra xem user đã review set này trước đó chưa
            boolean hasReviewed = reviewRepository.existsBySetIdAndUserId(setId, userId);
            if (hasReviewed) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng đã review set này trước đó");
            }

            Review createdReview = new Review();
            createdReview.setSet(s);
            createdReview.setUser(user);
            createdReview.setTotalStars(total_stars);

            reviewRepository.save(createdReview);

            return ResponseEntity.ok("Tạo review thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể tạo review");
        }
    }
}
