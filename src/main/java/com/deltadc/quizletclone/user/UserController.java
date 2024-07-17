package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.passwordreset.PasswordResetToken;
import com.deltadc.quizletclone.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

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

    //lay user dua tren filter
    @GetMapping("/list")
    public ResponseEntity<ResponseObject> listUsers(@Nullable @RequestParam("username") String username,
                                                    @Nullable @RequestParam("email") String email,
                                                    @Nullable @RequestParam("role") String role) {
        List<UserDTO> users = userService.listUsersByFilter(username, email, role);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Users found")
                        .data(users)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<ResponseObject> updateUserById(@PathVariable("userId") Long userId,
                                                         @NonNull @RequestBody User newUser) {
        User u = userService.updateUserById(userId, newUser);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Field changed")
                        .status(HttpStatus.OK)
                        .data(UserDTO.fromUserToUserDTO(u))
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
}
