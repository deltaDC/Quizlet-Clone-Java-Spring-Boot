package com.deltadc.quizletclone.set;


import com.deltadc.quizletclone.card.Card;
import com.deltadc.quizletclone.card.CardService;
import com.deltadc.quizletclone.response.ResponseObject;
import com.deltadc.quizletclone.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
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

    // tạo set mới
    @PostMapping("/create-set")
    public ResponseEntity<ResponseObject> createSet(@NonNull @RequestBody Set set) {
        Set createdSet = setService.createSet(set);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("set created")
                        .status(HttpStatus.OK)
                        .data(createdSet)
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

    //edit mot set theo setId
    @PutMapping("/edit/{setId}")
    public ResponseEntity<ResponseObject> editSetById(@PathVariable("setId") Long setId,
                                                      @NonNull @RequestBody Set newSet) {
        Set updatedSet = setService.editSetById(setId, newSet);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Set updated")
                        .status(HttpStatus.OK)
                        .data(updatedSet)
                        .build()
        );
    }

    //lay set theo filter
    @GetMapping("/list")
    public ResponseEntity<ResponseObject> getSetByFilter(@Nullable @RequestParam("title") String title,
                                                         @Nullable @RequestParam("isPublic") Boolean isPublic,
                                                         @Nullable @RequestParam("userId") Long userId,
                                                         @Nullable @RequestParam(defaultValue = "0") int page,
                                                         @Nullable @RequestParam(defaultValue = "30") int size) {
        Page<Set> setPage = setService.getSetByFilter(title, isPublic, userId, page, size);

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
