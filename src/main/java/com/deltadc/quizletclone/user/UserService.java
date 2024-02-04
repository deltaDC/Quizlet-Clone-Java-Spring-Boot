package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.set.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean signUp(User user) {
        // Kiểm tra xem người dùng có tồn tại dựa trên email hay không
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return false;
        }

        // Người dùng chưa tồn tại, thêm mới
        userRepository.save(user);
        return true;
    }

    public List<Set> getUserSets(String username) {
        return null;
    }


}
