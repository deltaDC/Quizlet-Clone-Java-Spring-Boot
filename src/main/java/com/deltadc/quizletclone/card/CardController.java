package com.deltadc.quizletclone.card;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class CardController {
    private final CardService cardService;

    // Lấy ra thông tin của tất cả card
    @GetMapping("/cards")
    public ResponseEntity<?> getAllCards() {
        return cardService.getAllCards();
    }

    // Lấy ra thông tin của 1 card theo id
    @GetMapping("/cards/{id}")
    public ResponseEntity<?> getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    // Tạo card mới
    @PostMapping("/create_card")
    public ResponseEntity<?> createCard(@RequestBody Card card) {
        return cardService.createCard(card);
    }

    // Chỉnh sửa card theo id
    @PutMapping("/cards/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody Card updatedCard) {
        return cardService.updateCard(id, updatedCard);
    }

    // Xóa card theo id
    @DeleteMapping("/cards/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        return cardService.deleteCard(id);
    }
}
