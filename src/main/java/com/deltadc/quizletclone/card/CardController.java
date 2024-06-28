package com.deltadc.quizletclone.card;

import com.deltadc.quizletclone.response.ResponseObject;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@AllArgsConstructor
@RequestMapping("/api/card")
public class CardController {
    private final CardService cardService;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final SetRepository setRepository;

    private boolean isEmptyCardInput(Card card) {
        return card.getFront_text().isEmpty() || card.getBack_text().isEmpty();
    }

    private boolean isCardOwner(Long cardId) {
        // Trích xuất thông tin người dùng từ JWT
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
        User user = userRepository.findByEmail(username).orElseThrow();
        Long userId = user.getUser_id();
        System.out.println("userId is " + userId);

        Card c = cardRepository.findById(cardId).orElseThrow();

        Long setId = c.getSet_id();
        Set s = setRepository.findById(setId).orElseThrow();
        System.out.println("user id of set is " + s.getUser_id());

        return Objects.equals(s.getUser_id(), userId);
    }

    // Lấy ra thông tin của tất cả card
    @GetMapping("/cards")
    public ResponseEntity<ResponseObject> getAllCards() {
        List<CardDTO> cardDTOS = cardService.getAllCards();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("all cards found")
                        .status(HttpStatus.OK)
                        .data(cardDTOS)
                        .build()
        );
    }

    // Lấy ra thông tin của 1 card theo id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCardById(@PathVariable Long id) {
        CardDTO cardDTO = cardService.getCardById(id);

        return ResponseEntity.ok(
                ResponseObject.builder()
                    .message("card found")
                    .status(HttpStatus.OK)
                    .data(cardDTO)
                    .build()
        );
    }

    // Lấy tất cả cards trong 1 set
    @GetMapping("/{set_id}/cards")
    public ResponseEntity<ResponseObject> getCardsInSet(@PathVariable("set_id") Long set_id,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "30") int size) {
        Page<CardDTO> cardDTOPage = cardService.getCardsInSet(set_id, page, size);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("all cards in set found")
                        .status(HttpStatus.OK)
                        .data(cardDTOPage)
                        .build()
        );
    }

    // Tạo card mới
    @PostMapping("/{set_id}/create_card")
    public ResponseEntity<ResponseObject> createCard(@PathVariable("set_id") Long set_id,
                                                     @RequestBody Card card) throws Exception {
        if(isEmptyCardInput(card)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Card should not be empty")
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }
        Card createdCard = cardService.createCard(card, set_id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Card created")
                        .status(HttpStatus.OK)
                        .data(createdCard)
                        .build()
        );
    }


    // Chỉnh sửa card theo id
    //TODO change from if to @PreAuthorize
    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseObject> updateCard(@PathVariable Long id,
                                                     @RequestBody Card updatedCard) {
        if(isEmptyCardInput(updatedCard)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("card should not be empty")
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .build()
            );
        }

        if(!isCardOwner(id)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("cannot update card because you are not the owner")
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .build()
            );
        }

        CardDTO cardDTO = cardService.updateCard(id, updatedCard);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Card updated")
                        .status(HttpStatus.OK)
                        .data(cardDTO)
                        .build()
        );
    }

    // Xóa card theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCard(@PathVariable Long id) {
        if(!isCardOwner(id)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Cannot delete card because not the owner")
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .build()
            );
        }

        cardService.deleteCard(id);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Card deleted")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    // tao mot list cac card theo set id
    @PostMapping("/{set_id}/create-cards")
    public ResponseEntity<ResponseObject> createCards(@PathVariable("set_id") Long setId,
                                                      @RequestBody List<Card> cardList) throws Exception {
        boolean allCardsValid = cardList.stream().allMatch(this::isEmptyCardInput);

        if(!allCardsValid) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Some cards are not valid")
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .build()
            );
        }

        List<Card> createdCards = cardService.createCards(setId, cardList);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cards created")
                        .status(HttpStatus.OK)
                        .data(createdCards)
                        .build()
        );
    }
}
