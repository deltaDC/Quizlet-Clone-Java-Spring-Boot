package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.email.EmailSender;
import com.deltadc.quizletclone.passwordreset.PasswordResetToken;
import com.deltadc.quizletclone.passwordreset.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailSender emailSender;

    @Value("${reset.password.url}")
    private String resetPasswordUrl;

    private boolean isUserOwner(User user) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userMail = userDetails.getUsername();

        User u = userRepository.findByEmail(userMail).orElseThrow();

        if(user.getRole().compareTo(Role.ADMIN) == 0) {
            System.out.println(user.getRole());
            return true;
        }

        return Objects.equals(u.getUser_id(), user.getUser_id());
    }

    private UserDTO getUserDTO(Optional<User> user) {
        return user.map(UserDTO::fromUserToUserDTO).orElse(null);
    }

    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(UserDTO::fromUserToUserDTO).toList();
    }

    public String deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        if(!isUserOwner(user)) {
            return "Unauthorized";
        }

        userRepository.delete(user);

        return "User deleted";
    }

    public User changeUserPassWordById(Long userId, User newUser) {
        User user = userRepository.findById(userId).orElseThrow();

        if(!isUserOwner(user)) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(user);

        return user;
    }

    public User resetPasswordByUserId(Long userId, User newUser) {
        User user = userRepository.findById(userId).orElseThrow();

        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(user);

        return user;
    }

    public UserDTO getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        return getUserDTO(user);
    }

    public UserDTO getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        return getUserDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return getUserDTO(user);
    }

    public User changeUsernameById(Long userId, User newUser) {
        User u = userRepository.findById(userId).orElseThrow();

        if(!isUserOwner(u)) {
            return null;
        }

        u.setUsername(newUser.getName());

        userRepository.save(u);

        return u;
    }

    public PasswordResetToken forgotPassword(String email) {

        Optional<User> u = userRepository.findByEmail(email);
        if(u.isEmpty()) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        Long user_id = u.get().getUser_id();

        PasswordResetToken passwordResetToken = new PasswordResetToken(
                token,
                u.get().getUser_id(),
                LocalDateTime.now().plusMinutes(15)
        );

        passwordResetTokenRepository.save(passwordResetToken);

        //them gui email tai day
        String link = resetPasswordUrl + token + "&userId=" + user_id;
//        emailSender.send(
//                email,
//                link
//        );

        return passwordResetToken;
    }

    @Transactional
    public String confirmResetPassword(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        Long user_id = passwordResetToken.getUser_id();

        LocalDateTime expiredAt = passwordResetToken.getExpiryDate();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        return String.format(
                "Email đã xác thực vui lòng quay trở lại trang " +
                        "<a href='http://localhost:3000/reset-password?user_id=%d'>Đổi mật khẩu</a>", user_id
        );
    }

}
