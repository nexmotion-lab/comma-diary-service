package com.coders.diaryservice.controller;

import com.coders.diaryservice.DTO.DiaryRequest;
import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "Diary API", description = "감정일기 API 입니다.")
@RestController
@RequestMapping("/api/vi/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Tag(name = "Diary API")
    @Operation(summary = "감정일기 리스트 가져오기", description = "감정일기 리스트를 불러옵니다.")
    @GetMapping("/")
    public ResponseEntity<List<Diary>> findDiariesByAccountId(int accountId)  {
        return ResponseEntity.ok(diaryService.findDiariesByAccountId(accountId));
    }

    @Tag(name = "Diary API")
    @Operation(summary = "감정일기 가져오기", description = "특정 감정일기를 불러옵니다.")
    @GetMapping("/{diaryNo}/{accountId}")
    public ResponseEntity<Diary> getDiaryByIdAndAccountId(@PathVariable int diaryNo, @PathVariable int accountId) {
        return diaryService.findDiaryByIdAndAccountId(diaryNo, accountId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Diary createDiary(@RequestBody DiaryRequest diaryRequest) {
        Diary diary = new Diary();
        diary.setContent(diaryRequest.getContent());
        diary.setCoreEmotionTag(diaryRequest.getCoreEmotionTag());
        diary.setDateCreated(new Date());

        return diaryService.createDiary(diary, diaryRequest.getEventTagIds(), diaryRequest.getEmotionTagIds());
    }
}
