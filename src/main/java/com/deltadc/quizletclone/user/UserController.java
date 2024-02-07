package com.deltadc.quizletclone.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // danh dau class nay la RESTful Controller
@RequiredArgsConstructor // tự động tạo một constructor chứa tất cả các trường được đánh dấu là final hoặc @NonNull.
public class UserController {

    private final UserService userService;


    // dang ky user moi
//    @PostMapping("/signup")
//    public ResponseEntity<String> signUp(@RequestBody User user) {
//        System.out.println("Dữ liệu nhận được từ phía client:");
//        System.out.println("Username: " + user.getUsername());
//        System.out.println("Email: " + user.getEmail());
//
//        // Thực hiện logic đăng ký người dùng trong service
//        boolean signUpSuccess = userService.signUp(user);
//
//        if (signUpSuccess) {
//            return ResponseEntity.ok("Đăng ký thành công");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đăng ký thất bại");
//        }
//    }
//
//    // dang nhap
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody User user) {
//        System.out.println("Dữ liệu nhận được từ phía client:");
//        System.out.println("Username: " + user.getUsername());
//        System.out.println("Email: " + user.getEmail());
//
//        boolean loginSuccess = userService.login(user);
//
//        if (loginSuccess) {
//            return ResponseEntity.ok("Đăng nhập thành công");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Đăng nhập thất bại");
//        }
//    }
//
//
//    // lay tat ca cac set cua 1 user
//    @GetMapping(path = "/{username}/sets")
//    public ResponseEntity<List<Set>> getProfile(@PathVariable("username") String username) {
//        List<Set> userSets = userService.getUserSets(username);
//
//        if (userSets != null && !userSets.isEmpty()) {
//            return ResponseEntity.ok(userSets);
//        } else {
//            // Trường hợp không tìm thấy sets hoặc sets rỗng
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/demo")
    public String helloWorld() {
        return "Hello World";
    }
}
