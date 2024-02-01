package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.set.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // danh dau class nay la RESTful Controller
@RequiredArgsConstructor // tự động tạo một constructor chứa tất cả các trường được đánh dấu là final hoặc @NonNull.
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{username}/sets")
    public ResponseEntity<List<Set>> getProfile(@PathVariable("username") String username) {
        List<Set> userSets = userService.getUserSets(username);

        if (userSets != null && !userSets.isEmpty()) {
            return ResponseEntity.ok(userSets);
        } else {
            // Trường hợp không tìm thấy sets hoặc sets rỗng
            return ResponseEntity.notFound().build();
        }
    }
}
