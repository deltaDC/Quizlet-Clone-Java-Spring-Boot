package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.email.EmailSender;
import com.deltadc.quizletclone.passwordreset.PasswordResetToken;
import com.deltadc.quizletclone.passwordreset.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
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
            log.atTrace().log("User is admin");
            return true;
        }

        return Objects.equals(u.getUser_id(), user.getUser_id());
    }

    public String deleteUserById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    if(!isUserOwner(user)) {
                        return "Unauthorized";
                    }

                    userRepository.delete(user);
                    return "User deleted";
                })
                .orElse("User not found");
    }

    public User updateUserById(Long userId, User newUser) {
        return userRepository.findById(userId)
                .map(u -> {
                    Optional.ofNullable(newUser.getName()).ifPresent(u::setUsername);
                    Optional.ofNullable(newUser.getPassword())
                            .ifPresent(password -> u.setPassword(passwordEncoder.encode(password)));
                    return userRepository.save(u);
                })
                .orElseThrow();
    }

    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(UserDTO::fromUserToUserDTO)
                .orElse(null);
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::fromUserToUserDTO)
                .orElse(null);
    }

    public PasswordResetToken forgotPassword(String email) {
        Long user_id = userRepository.findByEmail(email)
                .map(User::getUser_id)
                .orElse(null);

        String token = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user_id(user_id)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();

        passwordResetTokenRepository.save(passwordResetToken);

        String link = resetPasswordUrl + token + "&userId=" + user_id;
        emailSender.send(
                email,
                link
        );

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

    public List<UserDTO> listUsersByFilter(String username,
                                           String email,
                                           String role) {
        Specification<User> specification = UserSpecification.withDynamicQuery(username, email, role);
        return userRepository.findAll(specification).stream()
                .map(UserDTO::fromUserToUserDTO)
                .toList();
    }
}
