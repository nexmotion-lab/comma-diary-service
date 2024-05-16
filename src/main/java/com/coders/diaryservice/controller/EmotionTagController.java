package com.coders.diaryservice.controller;

import com.coders.diaryservice.entity.EmotionTag;
import com.coders.diaryservice.service.EmotionTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "emotionTag API", description = "감정태그 API 입니다.")
@RestController
@RequestMapping("/api/v1/emotion")
public class EmotionTagController {

    @Autowired
    private EmotionTagService emotionTagService;

    @GetMapping("/")
    public ResponseEntity<List<EmotionTag>> getAllEmotionTags() {
        List<EmotionTag> tags = emotionTagService.getAllEmotionTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmotionTag> getEmotionTagById(@PathVariable("id") int id) {
        return emotionTagService.getEmotionTagById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
