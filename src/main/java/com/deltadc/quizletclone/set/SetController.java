package com.deltadc.quizletclone.set;


import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.config.JwtService;
import com.deltadc.quizletclone.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/set")
public class SetController {

    private final SetService setService;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    @GetMapping("/{setId}")
    public ResponseEntity<List<Card>> getCardsInSet(@PathVariable Long setId, HttpServletRequest request) {
        // Lấy JWT token từ request
        String jwtToken = jwtService.extractTokenFromRequest(request);

        // Lấy email người dùng từ JWT token
        String currentUserEmail = jwtService.extractUsername(jwtToken);

        String currentUserId = String.valueOf(userRepository.findByEmail(currentUserEmail));

        // Lấy danh sách card trong set cho người dùng hiện tại và trả về
        return setService.getCardsInSet(setId, currentUserId);
    }

    @PostMapping("/create-set")
    public ResponseEntity<String> createSet(@RequestBody String json) {
        return setService.createSet(json);
    }
}
