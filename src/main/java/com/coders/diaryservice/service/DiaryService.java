package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.*;
import com.coders.diaryservice.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private EventTagRepository eventTagRepository;

    @Autowired
    private EmotionTagRepository emotionTagRepository;

    @Autowired
    private DiaryPerEventTagRepository diaryPerEventTagRepository;

    @Autowired
    private DiaryPerEmotionTagRepository diaryPerEmotionTagRepository;

    // 감정일기 생성
    @Transactional
    public Diary createDiary(Diary diary, List<Long> eventTagIds, List<Long> emotionTagIds) {
        // Save the diary
        Diary savedDiary = diaryRepository.save(diary);

        // Add event tags
        for (Long eventTagId : eventTagIds) {
            EventTag eventTag = eventTagRepository.findById(eventTagId).orElseThrow(() -> new RuntimeException("EventTag not found"));
            DiaryPerEventTagId diaryPerEventTagId = new DiaryPerEventTagId(savedDiary.getDiaryNo(), eventTag.getEventTagNo());
            DiaryPerEventTag diaryPerEventTag = new DiaryPerEventTag(diaryPerEventTagId, savedDiary, eventTag);
            diaryPerEventTagRepository.save(diaryPerEventTag);
        }

        // Add emotion tags
        for (Long emotionTagId : emotionTagIds) {
            EmotionTag emotionTag = emotionTagRepository.findById(emotionTagId).orElseThrow(() -> new RuntimeException("EmotionTag not found"));
            DiaryPerEmotionTagId diaryPerEmotionTagId = new DiaryPerEmotionTagId(savedDiary.getDiaryNo(), emotionTag.getEmotionTagNo());
            DiaryPerEmotionTag diaryPerEmotionTag = new DiaryPerEmotionTag(diaryPerEmotionTagId, savedDiary, emotionTag);
            diaryPerEmotionTagRepository.save(diaryPerEmotionTag);
        }

        return savedDiary;
    }

    // 감정일기 리스트 불러오기
    public List<Diary> getDiariesByAccountId(Long account_id) {
        return diaryRepository.findByAccountId(account_id);
    }
    public void deleteDiary(Long accountId, Long diaryNo) {
        Optional<Diary> diary = diaryRepository.findByDiaryNoAndAccountId(diaryNo, accountId);
        if (diary.isPresent()) {
            diaryRepository.delete(diary.get());
        } else {
            throw new IllegalArgumentException("Diary not found or you do not have permission to delete it");
        }
    }
    public Optional<Diary> getDiaryDetails(Long accountId, Long diaryNo) {
        return diaryRepository.findByDiaryNoAndAccountId(diaryNo, accountId);
    }
}
