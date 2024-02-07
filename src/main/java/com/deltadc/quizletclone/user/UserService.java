package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.set.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

//    private final UserRepository userRepository;
//
//    public boolean signUp(User user) {
//        // Kiểm tra xem người dùng có tồn tại dựa trên email hay không
//        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//
//        if (existingUser.isPresent()) {
//            return false;
//        }
//
//        // Người dùng chưa tồn tại, thêm mới
//        userRepository.save(user);
//        return true;
//    }
//
//    public boolean login(User user) {
//        // Kiểm tra xem người dùng có tồn tại dựa trên email hay không
//        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//
//        if (existingUser.isPresent()) {
//            return false;
//        }
//        return true;
//    }

    public List<Set> getUserSets(String username) {
        return null;
    }



}
