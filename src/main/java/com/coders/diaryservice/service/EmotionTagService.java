package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.entity.EmotionTag;
import com.coders.diaryservice.repository.DiaryRepository;
import com.coders.diaryservice.repository.EmotionTagRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmotionTagService {

    private final EmotionTagRepository emotionTagRepository;
    private final DiaryRepository diaryRepository;

    public EmotionTag findByOne(Long emotionTagNo, List<Long> emotionTagIds) {
        if (!emotionTagIds.contains(emotionTagNo)) {
            throw new IllegalArgumentException("Provided emotionTagNo is not in the emotionTagIds selected");
        }

        return emotionTagRepository.findEmotionTagByEmotionTagNo(emotionTagNo)
                .orElseThrow(() -> new EntityNotFoundException("EmotionTag not found"));
    }

    public Map<EmotionTag,Integer> findByYearMonthForStatistics(Long accountId, YearMonth yearMonth) {
        List<EmotionTag> emotionTags = emotionTagRepository
                .findEmotionTagsWithDiaryNoByAccountIdAndYearMonth(accountId, yearMonth.toString());

        Map<EmotionTag, Integer> emotionTagCountMap = new HashMap<>();

        for (EmotionTag emotionTag : emotionTags) {
            emotionTagCountMap.put(emotionTag, emotionTagCountMap.getOrDefault(emotionTag, 0) + 1);
        }

        return emotionTagCountMap;
    }

    public Map<Date, Long> findByDateForCalendar(Long accountId, YearMonth yearMonth) {
        List<Diary> diaries = diaryRepository.findByYearMonth(yearMonth.getYear(), yearMonth.getMonthValue(), accountId);

        return diaries.stream().map(diary -> new AbstractMap.SimpleEntry<>(
                diary.getDateCreated(), diary.getCoreEmotionTag().getEmotionTagNo()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
