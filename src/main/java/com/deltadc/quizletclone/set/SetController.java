package com.deltadc.quizletclone.set;


import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.card.CardService;
import com.deltadc.quizletclone.response.ResponseObject;
import com.deltadc.quizletclone.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/set")
public class SetController {
    private final SetService setService;
    private final CardService cardService;
    private final UserService userService;

    public SetDTO convertToDTO(Set set) {
        String username = userService.getUserById(set.getUser_id()).getUsername();

        List<Card> cards = cardService.getListCardsBySetId(set.getSet_id());

        return SetDTO.fromSetToSetDTO(set, username, cards.size());
    }

    private boolean isEmptySetInput(Set set) {
        return set.getTitle().isEmpty()
                || set.getDescription().isEmpty()
                || String.valueOf(set.isPublic()).isEmpty();
    }

    // tạo set mới
    @PostMapping("/create-set")
    public ResponseEntity<ResponseObject> createSet(@RequestBody Set set) {
        if(isEmptySetInput(set)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("set is invalid")
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }

        Set createdSet = setService.createSet(set);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("set created")
                        .status(HttpStatus.OK)
                        .data(createdSet)
                        .build()
        );
    }

    // lấy tất cả set của người dùng theo userId
    @GetMapping("/{user_id}/sets")
    public ResponseEntity<ResponseObject> getUserSets(@PathVariable("user_id") Long userId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "30") int size) {

        Page<Set> userSets = setService.getUserSets(userId, page, size);

        List<SetDTO> setDTOPage = userSets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        //TODO change to using mapper
        Map<String, Object> response = new HashMap<>();
        response.put("content", setDTOPage);
        response.put("totalPages", userSets.getTotalPages());

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("user's sets fetched")
                        .status(HttpStatus.OK)
                        .data(response)
                        .build()
        );
    }

    // xóa set dựa trên setId
    @DeleteMapping("/{setId}")
    public ResponseEntity<ResponseObject> deleteSet(@PathVariable("setId") Long setId) {
        String response = setService.deleteSet(setId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(response)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    //lay toan bo cac set
    @GetMapping("/get-all-sets")
    public ResponseEntity<ResponseObject> getAllSets(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "30") int size) {
        Page<Set> allSets = setService.getAllSets(page, size);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("all sets fetched")
                        .status(HttpStatus.OK)
                        .data(allSets)
                        .build()
        );
    }

    //edit mot set theo setId
    @PutMapping("/edit/{setId}")
    public ResponseEntity<ResponseObject> editSetById(@PathVariable("setId") Long setId,
                                                      @RequestBody Set newSet) {
        if(isEmptySetInput(newSet)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("set is invalid")
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }

        Set updatedSet = setService.editSetById(setId, newSet);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Set updated")
                        .status(HttpStatus.OK)
                        .data(updatedSet)
                        .build()
        );
    }

    //lay tat ca cac public set
    @GetMapping("/get-public-sets")
    public ResponseEntity<ResponseObject> getPublicSets(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "30") int size) {
        Page<Set> setPage = setService.getPublicSet(page, size);

        List<SetDTO> setDTOPage = setPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Public set fetched")
                        .status(HttpStatus.OK)
                        .data(setDTOPage)
                        .build()
        );
    }

    //tim set theo title
    @GetMapping("/title/{title}")
    public ResponseEntity<ResponseObject> getSetByTitle(@PathVariable("title") String title,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "30") int size) {
        Page<Set> setPage = setService.getSetByTitle(title, page, size);

        List<SetDTO> setDTOPage = setPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("content", setDTOPage);
        response.put("totalPages", setPage.getTotalPages());

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("sets with title fetched")
                        .status(HttpStatus.OK)
                        .data(response)
                        .build()
        );
    }
}
