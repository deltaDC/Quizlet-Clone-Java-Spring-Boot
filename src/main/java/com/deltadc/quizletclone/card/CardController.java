package com.deltadc.quizletclone.card;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/card")
@CrossOrigin(origins = "http://localhost:3000")
public class CardController {
    private final CardService cardService;

    // Lấy ra thông tin của tất cả card
    @GetMapping("/cards")
    public ResponseEntity<?> getAllCards() {
        return cardService.getAllCards();
    }

    // Lấy ra thông tin của 1 card theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    // Lấy tất cả cards trong 1 set
    @GetMapping("/{set_id}/cards")
    public ResponseEntity<?> getCardsInSet(@PathVariable("set_id") Long set_id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return cardService.getCardsInSet(set_id, page, size);
    }

    // Tạo card mới
    @PostMapping("/{set_id}/create_card")
    public ResponseEntity<?> createCard(@PathVariable("set_id") Long set_id, @RequestBody Card card) {
        return cardService.createCard(card, set_id);
    }

    // Chỉnh sửa card theo id
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody Card updatedCard) {
        return cardService.updateCard(id, updatedCard);
    }

    // Xóa card theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        return cardService.deleteCard(id);
    }

    // tao mot list cac card theo set id
    @PostMapping("/{set_id}/create-cards")
    public ResponseEntity<?> createCards(@PathVariable("set_id") Long setId, @RequestBody List<Card> cardList) {
        return cardService.createCards(setId, cardList);
    }
}
