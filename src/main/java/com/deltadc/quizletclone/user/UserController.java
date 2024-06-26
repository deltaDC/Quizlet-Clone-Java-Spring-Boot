package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.passwordreset.PasswordResetToken;
import com.deltadc.quizletclone.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // danh dau class nay la RESTful Controller
@RequiredArgsConstructor // tự động tạo một constructor chứa tất cả các trường được đánh dấu là final hoặc @NonNull.
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUser_id(user.getUser_id());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }

    //lay ra toan bo user
    @GetMapping("/get-all-users")
    public ResponseEntity<ResponseObject> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Users found")
                        .data(users)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    //xoa user theo id
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ResponseObject> deleteUserById(@PathVariable("userId") Long userId) {
        String response = userService.deleteUserById(userId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(response)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    //edit mat khau cua 1 user theo userId
    @PutMapping("/change-password/{userId}")
    public ResponseEntity<ResponseObject> changeUserPassWordById(@PathVariable("userId") Long userId, @RequestBody User newUser) {

        if(newUser.getPassword().isEmpty()) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Invalid")
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }

        User u = userService.changeUserPassWordById(userId, newUser);
        if(u == null) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.UNAUTHORIZED)
                            .build()
            );
        }

        UserDTO userDTO = convertToDTO(u);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("password changed")
                        .status(HttpStatus.OK)
                        .data(userDTO)
                        .build()
        );
    }

    //lay user dua theo user id
    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable("user_id") Long userId) {
        UserDTO userDTO = userService.getUserById(userId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("User found")
                        .data(userDTO)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    //lay user dua theo username
    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseObject> getUserByUsername(@PathVariable("username") String username) {
        UserDTO userDTO = userService.getUserByUsername(username);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("User found")
                        .data(userDTO)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    //lay user dua theo email
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
        UserDTO userDTO = userService.getUserByEmail(email);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("User found")
                        .data(userDTO)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping("/change-username/{userId}")
    public ResponseEntity<ResponseObject> changeUsernameById(@PathVariable("userId") Long userId, @RequestBody User newUser) {

        if(newUser.getName().isEmpty()) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Invalid")
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }

        User u = userService.changeUsernameById(userId, newUser);

        if(u == null) {
             return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.UNAUTHORIZED)
                            .build()
            );
        }

        UserDTO userDTO = convertToDTO(u);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("password changed")
                        .status(HttpStatus.OK)
                        .data(userDTO)
                        .build()
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseObject> forgotPassword(@RequestParam("email") String email) {
        PasswordResetToken token = userService.forgotPassword(email);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("token sent to email")
                        .status(HttpStatus.OK)
                        .data(token)
                        .build()
        );
    }

    @GetMapping("/confirm-reset-password")
    public String confirmResetPassword(@RequestParam("token") String token) {
        return userService.confirmResetPassword(token);
    }

    //edit mat khau cua 1 user theo userId
    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<ResponseObject> resetPasswordByUserId(@PathVariable("userId") Long userId, @RequestBody User newUser) {

        if(newUser.getPassword().isEmpty()) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Invalid")
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }

        User u = userService.resetPasswordByUserId(userId, newUser);
        if(u == null) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.UNAUTHORIZED)
                            .build()
            );
        }

        UserDTO userDTO = convertToDTO(u);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("password changed")
                        .status(HttpStatus.OK)
                        .data(userDTO)
                        .build()
        );
    }
}
