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

    // Lấy tất cả cards trong 1 set
    @GetMapping("/{set_id}/cards")
    public ResponseEntity<?> getCardsInSet(@PathVariable("set_id") Long set_id) {
        return cardService.getCardsInSet(set_id);
    }

    // Tạo card mới
    @PostMapping("/{set_id}/create_card")
    public ResponseEntity<?> createCard(@PathVariable("set_id") Long set_id,  @RequestBody Card card) {
        return cardService.createCard(card, set_id);
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
