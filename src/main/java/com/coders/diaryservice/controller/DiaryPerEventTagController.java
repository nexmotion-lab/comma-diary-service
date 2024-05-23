package com.coders.diaryservice.controller;

import com.coders.diaryservice.entity.DiaryPerEventTag;
import com.coders.diaryservice.entity.DiaryPerEventTagId;
import com.coders.diaryservice.service.DiaryPerEventTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diaryPerEventTags")
public class DiaryPerEventTagController {

    @Autowired
    private DiaryPerEventTagService service;

    @GetMapping
    public List<DiaryPerEventTag> getAll() {
        return service.findAll();
    }

    @GetMapping("/diary/{diaryNo}")
    public ResponseEntity<List<DiaryPerEventTag>> getByDiaryNo(@PathVariable int diaryNo) {
        List<DiaryPerEventTag> diaryPerEventTags = service.findByDiaryNo(diaryNo);
        if (diaryPerEventTags.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diaryPerEventTags);
    }

    @PostMapping
    public DiaryPerEventTag create(@RequestBody DiaryPerEventTag diaryPerEventTag) {
        return service.save(diaryPerEventTag);
    }

    @GetMapping("/{diaryNo}/{eventTagNo}")
    public ResponseEntity<DiaryPerEventTag> getById(@PathVariable int diaryNo, @PathVariable int eventTagNo) {
        DiaryPerEventTagId id = new DiaryPerEventTagId(diaryNo, eventTagNo);
        Optional<DiaryPerEventTag> diaryPerEventTag = service.findById(id);
        return diaryPerEventTag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{diaryNo}/{eventTagNo}")
    public ResponseEntity<DiaryPerEventTag> update(@PathVariable int diaryNo, @PathVariable int eventTagNo, @RequestBody DiaryPerEventTag diaryPerEventTagDetails) {
        DiaryPerEventTagId id = new DiaryPerEventTagId(diaryNo, eventTagNo);
        Optional<DiaryPerEventTag> diaryPerEventTag = service.findById(id);

        if (diaryPerEventTag.isPresent()) {
            DiaryPerEventTag existingTag = diaryPerEventTag.get();
            existingTag.setDiary(diaryPerEventTagDetails.getDiary());
            existingTag.setEventTag(diaryPerEventTagDetails.getEventTag());

            final DiaryPerEventTag updatedTag = service.save(existingTag);
            return ResponseEntity.ok(updatedTag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{diaryNo}/{eventTagNo}")
    public ResponseEntity<Void> delete(@PathVariable int diaryNo, @PathVariable int eventTagNo) {
        DiaryPerEventTagId id = new DiaryPerEventTagId(diaryNo, eventTagNo);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
