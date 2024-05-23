package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.*;
import com.coders.diaryservice.repository.*;
import com.netflix.discovery.converters.Auto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private EmotionTagRepository emotionTagRepository;

    @Autowired
    private EventTagRepository eventTagRepository;

    @Autowired
    private DiaryPerEventTagRepository diaryPerEventTagRepository;

    @Autowired
    private DiaryPerEmotionTagRepository diaryPerEmotionTagRepository;

    @Transactional
    public Diary createDiary(Diary diary, List<Long> eventTagIds, List<Long> emotionTagIds) {
        // Save the diary
        Diary savedDiary = diaryRepository.save(diary);

        // Add event tags
        for (Long eventTagId : eventTagIds) {
            EventTag eventTag = eventTagRepository.findById(Math.toIntExact(eventTagId)).orElseThrow(() -> new RuntimeException("EventTag not found"));
            DiaryPerEventTag diaryPerEventTag = new DiaryPerEventTag();
            diaryPerEventTag.setDiary(savedDiary);
            diaryPerEventTag.setEventTag(eventTag);
            diaryPerEventTagRepository.save(diaryPerEventTag);
        }

        // Add emotion tags
        for (Long emotionTagId : emotionTagIds) {
            EmotionTag emotionTag = emotionTagRepository.findById(Math.toIntExact(emotionTagId)).orElseThrow(() -> new RuntimeException("EmotionTag not found"));
            DiaryPerEmotionTag diaryPerEmotionTag = new DiaryPerEmotionTag();
            diaryPerEmotionTag.setDiary(savedDiary);
            diaryPerEmotionTag.setEmotionTag(emotionTag);
            diaryPerEmotionTagRepository.save(diaryPerEmotionTag);
        }

        return savedDiary;
    }

    public Diary saveDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    public List<Diary> findDiariesByAccountId(int accountId) {
        return diaryRepository.findByAccountId(accountId);
    }

    public Optional<Diary> findDiaryByIdAndAccountId(int diaryNo, int accountId) {
        return diaryRepository.findByDiaryNoAndAccountId(diaryNo, accountId);
    }
    public Diary updateDiary(int id, Diary diaryDetails) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diary not found with id " + id));
        // todo::
        diary.setAccountId(diary.getAccountId());
        diary.setContent(diary.getContent());

        return diaryRepository.save(diary);
    }

    public void deleteDiary(int id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diary not found with id " + id));
        diaryRepository.delete(diary);
    }
}
