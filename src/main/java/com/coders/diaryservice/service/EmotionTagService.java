package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.entity.EmotionTag;
import com.coders.diaryservice.repository.DiaryRepository;
import com.coders.diaryservice.repository.EmotionTagRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public Map<String, Long> findByDateForCalendar(Long accountId, YearMonth yearMonth) {
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        LocalDate startDate = firstDayOfMonth.minusDays(14);
        LocalDate endDate = lastDayOfMonth.plusDays(14);

        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        List<Diary> diaries = diaryRepository.findDiariesWithinDateRange(start, end, accountId);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return diaries.stream()
                .map(diary -> {
                    Date date = diary.getDateCreated();
                    String formattedDate = formatter.format(date);
                    return new AbstractMap.SimpleEntry<>(formattedDate, diary.getCoreEmotionTag().getEmotionTagNo());
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
