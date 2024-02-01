package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.set.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Set> getUserSets(String username) {
        return null;
    }
}
