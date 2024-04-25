package com.deltadc.quizletclone.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOS = userList.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setUser_id(user.getUser_id());
                    userDTO.setUsername(user.getName());
                    userDTO.setEmail(user.getEmail());
                    userDTO.setRole(user.getRole());

                    return userDTO;
                }).toList();


        return ResponseEntity.ok(userDTOS);
    }

    public ResponseEntity<?> deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        userRepository.delete(user);

        return ResponseEntity.ok("da xoa user " + userId);
    }

    public User changeUserPassWordById(Long userId, User newUser) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(user);

        return user;
    }

    public ResponseEntity<?> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUser_id(user.get().getUser_id());
            userDTO.setUsername(user.get().getName());
            userDTO.setEmail(user.get().getEmail());
            userDTO.setRole(user.get().getRole());

            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User khong ton tai");
        }
    }

    public ResponseEntity<?> getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUser_id(user.get().getUser_id());
            userDTO.setUsername(user.get().getName());
            userDTO.setEmail(user.get().getEmail());
            userDTO.setRole(user.get().getRole());

            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User khong ton tai");
        }
    }

    public ResponseEntity<?> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUser_id(user.get().getUser_id());
            userDTO.setUsername(user.get().getName());
            userDTO.setEmail(user.get().getEmail());
            userDTO.setRole(user.get().getRole());

            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User khong ton tai");
        }
    }

    public User changeUsernameById(Long userId, User newUser) {
        User u = userRepository.findById(userId).orElseThrow();

        u.setUsername(newUser.getName());

        userRepository.save(u);

        return u;
    }
}
