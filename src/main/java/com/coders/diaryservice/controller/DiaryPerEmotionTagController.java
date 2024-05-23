package com.coders.diaryservice.controller;

import com.coders.diaryservice.entity.DiaryPerEmotionTag;
import com.coders.diaryservice.entity.DiaryPerEmotionTagId;
import com.coders.diaryservice.service.DiaryPerEmotionTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diary-emotion")
public class DiaryPerEmotionTagController {

    @Autowired
    private DiaryPerEmotionTagService service;

    @GetMapping
    public List<DiaryPerEmotionTag> getAll() {
        return service.findAll();
    }

    @GetMapping("/{diaryNo}")
    public ResponseEntity<List<DiaryPerEmotionTag>> getByDiaryNo(@PathVariable int diaryNo) {
        List<DiaryPerEmotionTag> diaryPerEmotionTags = service.findByDiaryNo(diaryNo);
        if (diaryPerEmotionTags.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diaryPerEmotionTags);
    }

    @PostMapping
    public DiaryPerEmotionTag create(@RequestBody DiaryPerEmotionTag diaryPerEmotionTag) {
        return service.save(diaryPerEmotionTag);
    }

    @PutMapping("/{diaryNo}/{emotionTagNo}")
    public ResponseEntity<DiaryPerEmotionTag> update(@PathVariable int diaryNo, @PathVariable int emotionTagNo, @RequestBody DiaryPerEmotionTag diaryPerEmotionTagDetails) {
        DiaryPerEmotionTagId id = new DiaryPerEmotionTagId(diaryNo, emotionTagNo);
        Optional<DiaryPerEmotionTag> diaryPerEmotionTag = service.findById(id);

        if (diaryPerEmotionTag.isPresent()) {
            DiaryPerEmotionTag existingTag = diaryPerEmotionTag.get();
            // Update fields here
            existingTag.setDiaryNo(diaryPerEmotionTagDetails.getDiaryNo());
            existingTag.setEmotionTagNo(diaryPerEmotionTagDetails.getEmotionTagNo());

            final DiaryPerEmotionTag updatedTag = service.save(existingTag);
            return ResponseEntity.ok(updatedTag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{diaryNo}/{emotionTagNo}")
    public ResponseEntity<Void> delete(@PathVariable int diaryNo, @PathVariable int emotionTagNo) {
        DiaryPerEmotionTagId id = new DiaryPerEmotionTagId(diaryNo, emotionTagNo);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

