package com.deltadc.quizletclone.card;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
public class CardController {
    private final CardService cardService;

    // Lấy ra thông tin của tất cả card
    @GetMapping("/cards")
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    // Lấy ra thông tin của 1 card theo id
    @GetMapping("/cards/{id}")
    public Card getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    // Tạo card mới
    @PostMapping("create_card")
    public Card createCard(@RequestBody Card card) {
        return cardService.createCard(card);
    }

    // Chỉnh sửa card theo id
    @PutMapping("/cards/{id}")
    public Card updateCard(@PathVariable Long id, @RequestBody Card updatedCard) {
        return cardService.updateCard(id, updatedCard);
    }

    // Xóa card theo id
    @DeleteMapping("/cards/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }
}
