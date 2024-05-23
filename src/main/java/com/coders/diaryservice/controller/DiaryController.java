package com.coders.diaryservice.controller;

import com.coders.diaryservice.dto.DiaryRequest;
import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.service.DiaryService;
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
    public Diary createDiary(@RequestBody DiaryRequest diaryRequest) {
        Diary diary = new Diary();
        diary.setAccount_id(diaryRequest.getAccount_id());
        diary.setContent(diaryRequest.getContent());
        diary.setCoreEmotionTag(diaryRequest.getCoreEmotionTag());
        diary.setDateCreated(new Date());

        return diaryService.createDiary(diary, diaryRequest.getEventTagIds(), diaryRequest.getEmotionTagIds());
    }
}
