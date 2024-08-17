package com.coders.diaryservice.controller;

import com.coders.diaryservice.dto.InteractionDto;
import com.coders.diaryservice.entity.Interactions;
import com.coders.diaryservice.repository.DiaryRepository;
import com.coders.diaryservice.service.InteractionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/interaction")
@AllArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @PostMapping()
    private ResponseEntity<Void> addInteraction(@RequestParam String keyword,
                                         @RequestParam List<String> texts,
                                         @RequestParam MultipartFile image) throws IOException {
        interactionService.insertInteraction(keyword, texts, image.getBytes());
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    private ResponseEntity<List<InteractionDto>> getInteraction() {
        return ResponseEntity.ok(interactionService.getInteractions());
    }

    @GetMapping("/user")
    private ResponseEntity<InteractionDto> getInteractionUser(@RequestHeader("X-User-Id") Long accountId) {

        InteractionDto interactionDto = new InteractionDto(interactionService.getInteractions(accountId));
        return ResponseEntity.ok(interactionDto);
    }

}
