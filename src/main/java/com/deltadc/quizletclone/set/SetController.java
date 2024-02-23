package com.deltadc.quizletclone.set;


import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.config.JwtService;
import com.deltadc.quizletclone.user.UserRepository;
import com.deltadc.quizletclone.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/set")
public class SetController {
    private final UserService userService;
    private final SetService setService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private SetDTO convertToDTO(Set set) {
        SetDTO setDTO = new SetDTO();
        setDTO.setSetId(set.getSet_id());
        setDTO.setTitle(set.getTitle());
        setDTO.setDescription(set.getDescription());
        setDTO.setCreatedAt(set.getCreatedAt());
        setDTO.setUpdatedAt(set.getUpdatedAt());
        setDTO.setPublic(set.isPublic());
        return setDTO;
    }

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

    // lấy tất cả set của người dùng
    @GetMapping("/{user_id}/sets")
    public ResponseEntity<List<SetDTO>> getUserSets(@PathVariable("user_id") Long userId) {
        List<Set> userSets = userService.getUserSets(userId);
        List<SetDTO> userSetDTOs = userSets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userSetDTOs);
    }

    // tạo set mới
    @PostMapping("/create-set")
    public ResponseEntity<String> createSet(@RequestBody String json) {
        return setService.createSet(json);
    }

    // xóa set dựa trên setId
    @DeleteMapping("/{setId}")
    public ResponseEntity<String> deleteSet(@PathVariable("setId") Long setId) {
        return setService.deleteSet(setId);
    }
}
