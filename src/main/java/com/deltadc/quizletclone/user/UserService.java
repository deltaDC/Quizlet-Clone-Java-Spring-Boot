package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.folder.Folder;
import com.deltadc.quizletclone.folder.FolderRepository;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SetRepository setRepository;
    private final FolderRepository folderRepository;



    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userRepository.findAll();

        return ResponseEntity.ok(userList);
    }

    public ResponseEntity<?> deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        userRepository.delete(user);

        return ResponseEntity.ok("da xoa user " + userId);
    }
}
