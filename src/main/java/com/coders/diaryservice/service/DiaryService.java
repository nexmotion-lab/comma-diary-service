package com.coders.diaryservice.service;

import com.coders.diaryservice.dto.DiaryDto;
import com.coders.diaryservice.dto.mapper.DiaryMapper;
import com.coders.diaryservice.entity.*;
import com.coders.diaryservice.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DiaryService {

    private final DiaryRepository diaryRepository;

    private final EventTagDao eventTagDao;

    private final EmotionTagDao emotionTagDao;

    private final AccountPerEventTagRepository accountPerEventTagRepository;

    @Transactional
    public void createDiary(Diary diary, List<Long> eventTagIds, List<Long> emotionTagIds) {
        Diary savedDiary = diaryRepository.save(diary);
        eventTagDao.batchInsertEventTags(eventTagIds, savedDiary.getDiaryNo());
        emotionTagDao.batchInsertEmotionTags(emotionTagIds, diary.getDiaryNo());
    }

    public List<DiaryDto> getDiaries(Long lastNo, int size, Long accountId, Date startDate, Date endDate, List<Long> emotionTagIds, List<Long> eventTagIds) {
        Pageable pageable = Pageable.ofSize(size);
        Page<Diary> diaryPage = diaryRepository.findDiariesByCriteria(lastNo, accountId, startDate, endDate, emotionTagIds, eventTagIds, pageable);
        return diaryPage.stream()
                .map(DiaryMapper::toDto)
                .collect(Collectors.toList());
    }
    public void deleteDiary(Long accountId, Long diaryNo) {
        Optional<Diary> diary = diaryRepository.findByDiaryNoAndAccountId(diaryNo, accountId);
        if (diary.isPresent()) {
            diaryRepository.delete(diary.get());
        } else {
            throw new IllegalArgumentException("Diary not found or you do not have permission to delete it");
        }
    }


    public List<EventTag> getEventTagsByAccountId(Long accountId) {
        return accountPerEventTagRepository.findByAccountId(accountId).orElseThrow(
                () -> new EntityNotFoundException("EventTag not found")).getEventTags();
    }
}
