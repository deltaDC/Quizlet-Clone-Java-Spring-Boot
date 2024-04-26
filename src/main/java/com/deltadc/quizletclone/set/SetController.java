package com.deltadc.quizletclone.set;


import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.card.CardRepository;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public SetDTO convertToDTO(Set set) {
        User user = userRepository.findById(set.getUser_id()).orElseThrow();
        String username = user.getName();

        List<Card> cards = cardRepository.findListCardsBySetId(set.getSet_id());

        SetDTO setDTO = new SetDTO();
        setDTO.setSetId(set.getSet_id());
        setDTO.setTitle(set.getTitle());
        setDTO.setDescription(set.getDescription());
        setDTO.setCreatedAt(set.getCreatedAt());
        setDTO.setUpdatedAt(set.getUpdatedAt());
        setDTO.setPublic(set.isPublic());
        setDTO.setTermCount((long) cards.size());
        setDTO.setOwnerName(username);
        return setDTO;
    }

    private boolean isEmptySetInput(Set set) {
        return set.getTitle().isEmpty()
                || set.getDescription().isEmpty()
                || String.valueOf(set.isPublic()).isEmpty();
    }

    // tạo set mới
    @PostMapping("/create-set")
    public ResponseEntity<?> createSet(@RequestBody Set set) {
        if(isEmptySetInput(set)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("khong duoc de trong");
        }

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
    @GetMapping("/get-all-sets")
    public ResponseEntity<?> getAllSets() {
        return setService.getAllSets();
    }

    //edit mot set theo setId
    @PutMapping("/edit/{setId}")
    public ResponseEntity<?> editSetById(@PathVariable("setId") Long setId, @RequestBody Set newSet) {
        if(isEmptySetInput(newSet)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("khong duoc de trong");
        }

        return setService.editSetById(setId, newSet);
    }

    //lay tat ca cac public set
    @GetMapping("/get-public-sets")
    public ResponseEntity<?> getPublicSets() {
        List<Set> setList = setService.getPublicSet();

        List<SetDTO> userSetDTOs = setList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userSetDTOs);
    }

    //tim set theo title
    @GetMapping("/title/{title}")
    public ResponseEntity<?> getSetByTitle(@PathVariable("title") String title) {
        return setService.getSetByTitle(title);
    }
}
