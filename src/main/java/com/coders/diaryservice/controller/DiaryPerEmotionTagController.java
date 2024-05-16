package com.coders.diaryservice.controller;

import com.coders.diaryservice.entity.DiaryPerEmotionTag;
import com.coders.diaryservice.entity.DiaryPerEmotionTagId;
import com.coders.diaryservice.service.DiaryPerEmotionTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaryemotion")
public class DiaryPerEmotionTagController {

    @Autowired
    private DiaryPerEmotionTagService service;

    @PostMapping
    public ResponseEntity<DiaryPerEmotionTag> create(@RequestBody DiaryPerEmotionTag diaryPerEmotionTag) {
        return ResponseEntity.ok(service.save(diaryPerEmotionTag));
    }

    @GetMapping
    ResponseEntity<List<DiaryPerEmotionTag>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{diaryNo}")
    public ResponseEntity<List<DiaryPerEmotionTag>> findByDiaryNo(@PathVariable int diaryNo) {
        List<DiaryPerEmotionTag> diaryPerEmotionTags = service.findByDiaryNo(diaryNo);
        if (!diaryPerEmotionTags.isEmpty()) {
            return ResponseEntity.ok(diaryPerEmotionTags);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{diaryNo}/{emotionTagNo}")
    public ResponseEntity<Void> delete(@PathVariable int diaryNo, @PathVariable int emotionTagNo) {
        DiaryPerEmotionTagId id = new DiaryPerEmotionTagId(diaryNo, emotionTagNo);
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}


