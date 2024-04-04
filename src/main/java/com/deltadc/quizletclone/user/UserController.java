package com.deltadc.quizletclone.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // danh dau class nay la RESTful Controller
@RequiredArgsConstructor // tự động tạo một constructor chứa tất cả các trường được đánh dấu là final hoặc @NonNull.
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    //lay ra toan bo user
    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }
}
