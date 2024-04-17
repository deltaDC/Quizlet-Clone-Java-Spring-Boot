package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.folder.Folder;
import com.deltadc.quizletclone.folder.FolderRepository;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOS = userList.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setUser_id(user.getUser_id());
                    userDTO.setUsername(user.getUsername());
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

    public ResponseEntity<?> changeUserPassWordById(Long userId, User newUser) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setPassword(newUser.getPassword());

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
