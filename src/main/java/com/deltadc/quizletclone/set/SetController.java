package com.deltadc.quizletclone.set;


import com.deltadc.quizletclone.config.JwtService;
import com.deltadc.quizletclone.user.UserRepository;
import com.deltadc.quizletclone.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/set")
@CrossOrigin(origins = "http://localhost:3000")
public class SetController {
    private final SetService setService;

    public SetDTO convertToDTO(Set set) {
        SetDTO setDTO = new SetDTO();
        setDTO.setSetId(set.getSet_id());
        setDTO.setTitle(set.getTitle());
        setDTO.setDescription(set.getDescription());
        setDTO.setCreatedAt(set.getCreatedAt());
        setDTO.setUpdatedAt(set.getUpdatedAt());
        setDTO.setPublic(set.isPublic());
        return setDTO;
    }

    // tạo set mới
    @PostMapping("/create-set")
    public ResponseEntity<?> createSet(@RequestBody Set set) {
        return setService.createSet(set);
    }

    // lấy tất cả set của người dùng theo userId
    @GetMapping("/{user_id}/sets")
    public ResponseEntity<?> getUserSets(@PathVariable("user_id") Long userId) {
        List<Set> userSets = setService.getUserSets(userId);
        List<SetDTO> userSetDTOs = userSets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userSetDTOs);
    }

    // xóa set dựa trên setId
    @DeleteMapping("/{setId}")
    public ResponseEntity<?> deleteSet(@PathVariable("setId") Long setId) {
        return setService.deleteSet(setId);
    }

    //lay toan bo cac set
    @GetMapping("get-all-sets")
    public ResponseEntity<?> getAllSets() {
        return setService.getAllSets();
    }

}
