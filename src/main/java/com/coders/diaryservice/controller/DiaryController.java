package com.coders.diaryservice.controller;

import com.coders.diaryservice.dto.DiaryDto;
import com.coders.diaryservice.dto.DiaryRequest;
import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.service.DiaryService;
import com.coders.diaryservice.service.EmotionTagService;
import com.coders.diaryservice.service.EventTagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class DiaryController {


    private final DiaryService diaryService;

    private final EmotionTagService emotionTagService;

    private final EventTagService eventTagService;


    @PostMapping("/diary")
    public ResponseEntity<Void> createDiary(@RequestBody DiaryRequest diaryRequest, HttpServletRequest request) {
        Long accountId = Long.parseLong(request.getHeader("X-User-Id"));
        Diary diary = Diary.builder()
                        .accountId(accountId)
                        .content(diaryRequest.getContent())
                        .coreEmotionTag(emotionTagService.findByOne(diaryRequest.getCoreEmotionTagId(), diaryRequest.getEmotionTagIds()))
                        .dateCreated(new Date()).build();
        diaryService.createDiary(diary, diaryRequest.getEventTagIds(), diaryRequest.getEmotionTagIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/eventTag")
    public ResponseEntity<Void> createEventTags(@RequestParam String eventTagName, HttpServletRequest request) {
        eventTagService.createEventTagAndUpdateUser(eventTagName,
                Long.parseLong(request.getHeader("X-User-Id")));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/diary")
    public ResponseEntity<List<DiaryDto>> getDiaries(
            @RequestParam(required = false) Long lastNo,
            @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        List<DiaryDto> diaries = diaryService.getDiaries(lastNo, size, Long.parseLong(request.getHeader("X-User-Id")));
        return ResponseEntity.ok(diaries);
    }

    @DeleteMapping("/{diaryNo}")
    public void deleteDiary(@PathVariable Long diaryNo, HttpServletRequest request) {
        String accountIdStr = request.getHeader("account_id");
        if (accountIdStr == null || accountIdStr.isEmpty()) {
            throw new IllegalArgumentException("Missing account_id header");
        }
        Long accountId = Long.parseLong(accountIdStr);
        diaryService.deleteDiary(accountId, diaryNo);
    }




}
