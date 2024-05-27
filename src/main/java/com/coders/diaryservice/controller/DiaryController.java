package com.coders.diaryservice.controller;

import com.coders.diaryservice.dto.DiaryRequest;
import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.service.DiaryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
}
