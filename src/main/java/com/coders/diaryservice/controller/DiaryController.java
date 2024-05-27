package com.coders.diaryservice.controller;

import com.coders.diaryservice.dto.DiaryRequest;
import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.service.DiaryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @PostMapping
    public Diary createDiary(@RequestBody DiaryRequest diaryRequest, HttpServletRequest request) {
        String accountIdStr = request.getHeader("account_id");
        if (accountIdStr == null || accountIdStr.isEmpty()) {
            throw new IllegalArgumentException("Missing account_id header");
        }

        Long accountId;
        try {
            accountId = Long.parseLong(accountIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid account_id format");
        }

        Diary diary = new Diary();
        diary.setAccountId(accountId);
        diary.setContent(diaryRequest.getContent());
        diary.setCoreEmotionTag(diaryRequest.getCoreEmotionTag());
        diary.setDateCreated(new Date());

        return diaryService.createDiary(diary, diaryRequest.getEventTagIds(), diaryRequest.getEmotionTagIds());
    }

    @GetMapping
    public List<Diary> getDiariesByAccountId(HttpServletRequest request) {
        String accountIdStr = request.getHeader("account_id");
        if (accountIdStr == null || accountIdStr.isEmpty()) {
            throw new IllegalArgumentException("Missing account_id header");
        }
        Long accountId = Long.parseLong(accountIdStr);
        return diaryService.getDiariesByAccountId(accountId);
    }

    @GetMapping("/{diaryNo}")
    public Optional<Diary> getDiaryDetails(@PathVariable Long diaryNo, HttpServletRequest request) {
        String accountIdStr = request.getHeader("account_id");
        if (accountIdStr == null || accountIdStr.isEmpty()) {
            throw new IllegalArgumentException("Missing account_id header");
        }
        Long accountId = Long.parseLong(accountIdStr);
        return diaryService.getDiaryDetails(accountId, diaryNo);
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

    @PostMapping("/{diaryNo}/events")
    public ResponseEntity<String> addEventToDiary(
            @RequestHeader("account_id") Long accountId,
            @PathVariable Long diaryNo,
            @RequestBody Map<String, String> requestBody) {

        String eventName = requestBody.get("eventName");

        if (eventName == null || eventName.isEmpty()) {
            return ResponseEntity.badRequest().body("Event name is required");
        }

        diaryService.addEventToDiary(diaryNo, accountId, eventName);
        return ResponseEntity.ok("Event added successfully");
    }

    @GetMapping("/events")
    public List<Map<String, Object>> getDistinctEventTagsByAccountId(HttpServletRequest request) {
        String accountIdStr = request.getHeader("account_id");
        if (accountIdStr == null || accountIdStr.isEmpty()) {
            throw new IllegalArgumentException("Missing account_id header");
        }
        Long accountId = Long.parseLong(accountIdStr);
        List<Object[]> eventTags = diaryService.getDistinctEventTagsByAccountId(accountId);

        // Convert the result to a list of maps for better JSON representation
        return eventTags.stream().map(tag -> Map.of("eventTagNo", tag[0], "name", tag[1])).toList();
    }
}
