package com.coders.diaryservice.service;

import com.coders.diaryservice.dto.DiaryDto;
import com.coders.diaryservice.dto.EmotionTagDto;
import com.coders.diaryservice.dto.mapper.DiaryMapper;
import com.coders.diaryservice.entity.*;
import com.coders.diaryservice.exception.diary.DiaryException;
import com.coders.diaryservice.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DiaryService {

    private final DiaryRepository diaryRepository;

    private final EventTagDao eventTagDao;

    private final EmotionTagDao emotionTagDao;

    private final AccountPerEventTagRepository accountPerEventTagRepository;

    private final DiaryPerEmotionTagRepository diaryPerEmotionTagRepository;

    private final EmotionTagRepository emotionTagRepository;

    private final EventTagRepository eventTagRepository;

    private final DiaryPerEventTagRepository diaryPerEventTagRepository;

    @Transactional
    public void updateDiaryContent(Long diaryNo, Long accountId, String diaryContent) {
        Diary diary = diaryRepository.findById(diaryNo)
                .filter(d -> d.getAccountId().equals(accountId))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 일기가 존재하지 않거나 올바르지 않은 사용자 요청입니다."));

        diary.setContent(diaryContent);
        diaryRepository.save(diary);
    }

    @Transactional
    public void updateDiaryPerEmotionTag(Long accountId, Long diaryNo, List<Long> newEmotionTagNos) {
        Diary diary = diaryRepository.findById(diaryNo)
                .filter(d -> d.getAccountId().equals(accountId))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 일기가 존재하지 않거나 올바르지 않은 사용자 요청입니다."));

        List<DiaryPerEmotionTag> existingTags = diary.getEmotionTags();

        Set<Long> existingEmotionTagNos = existingTags.stream()
                .map(tag -> tag.getEmotionTag().getEmotionTagNo())
                .collect(Collectors.toSet());

        List<DiaryPerEmotionTag> tagsToRemove = existingTags.stream()
                .filter(tag -> !newEmotionTagNos.contains(tag.getEmotionTag().getEmotionTagNo()))
                .collect(Collectors.toList());

        List<DiaryPerEmotionTag> tagsToAdd = newEmotionTagNos.stream()
                .filter(emotionTagNo -> !existingEmotionTagNos.contains(emotionTagNo))
                .map(emotionTagNo -> {
                    return DiaryPerEmotionTag.builder()
                            .id(new DiaryPerEmotionTagId(diaryNo, emotionTagNo))
                            .diary(diary)
                            .emotionTag(emotionTagRepository.findById(emotionTagNo).orElseThrow())
                            .build();
                })
                .collect(Collectors.toList());

        if (!tagsToRemove.isEmpty()) {
            diaryPerEmotionTagRepository.deleteAllInBatch(tagsToRemove);
        }
        if (!tagsToAdd.isEmpty()) {
            diaryPerEmotionTagRepository.saveAll(tagsToAdd);
        }
    }

    @Transactional
    public void updateDiaryPerEventTag(Long accountId, Long diaryNo, List<Long> newEventTagNos) {
        Diary diary = diaryRepository.findById(diaryNo)
                .filter(d -> d.getAccountId().equals(accountId))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 일기가 존재하지 않거나 올바르지 않은 사용자 요청입니다."));

        List<EventTag> eventTags = accountPerEventTagRepository.findByAccountId(accountId)
                .orElseThrow().getEventTags();

        Set<Long> eventTagNosSet = eventTags.stream()
                .map(EventTag::getEventTagNo)
                .collect(Collectors.toSet());

        boolean allMatch = eventTagNosSet.containsAll(newEventTagNos);

        if (!allMatch) {
            throw new IllegalArgumentException("특정 이벤트태그가 사용자가 사용할 수 없는 이벤트태그 입니다.");
        }

        List<DiaryPerEventTag> existingTags = diary.getEventTags();

        Set<Long> existingEventTagNos = existingTags.stream()
                .map(tag -> tag.getEventTag().getEventTagNo())
                .collect(Collectors.toSet());

        List<DiaryPerEventTag> tagsToRemove = existingTags.stream()
                .filter(tag -> !newEventTagNos.contains(tag.getEventTag().getEventTagNo()))
                .collect(Collectors.toList());

        List<DiaryPerEventTag> tagsToAdd = newEventTagNos.stream()
                .filter(eventTagNo -> !existingEventTagNos.contains(eventTagNo))
                .map(eventTagNo -> {
                    return DiaryPerEventTag.builder()
                            .id(new DiaryPerEventTagId(diaryNo, eventTagNo))
                            .diary(diary)
                            .eventTag(eventTagRepository.findById(eventTagNo).orElseThrow())
                            .build();
                })
                .collect(Collectors.toList());

        if (!tagsToRemove.isEmpty()) {
            diaryPerEventTagRepository.deleteAllInBatch(tagsToRemove);
        }
        if (!tagsToAdd.isEmpty()) {
            diaryPerEventTagRepository.saveAll(tagsToAdd);
        }
    }

    @Transactional
    public void createDiary(Diary diary, List<Long> eventTagIds, List<Long> emotionTagIds, Long accountId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(diary.getDateCreated());

        LocalDate localDate = LocalDate.parse(formattedDate);

        Optional<Diary> existingDiary = diaryRepository.findByDateCreated(localDate, accountId);
        log.info(existingDiary.toString());
        if (existingDiary.isPresent()) {
            throw new DiaryException("이미 오늘 작성한 일기가 존재합니다.");
        }

        Diary savedDiary = diaryRepository.save(diary);
        log.info(existingDiary.toString());
        eventTagDao.batchInsertEventTags(eventTagIds, savedDiary.getDiaryNo());
        emotionTagDao.batchInsertEmotionTags(emotionTagIds, diary.getDiaryNo());
    }

    public List<DiaryDto> getDiaries(Long lastNo, int size, Long accountId, LocalDate startDate,
                                     LocalDate endDate, List<Long> emotionTagIds, List<Long> eventTagIds, boolean orderByDesc) {
        Pageable pageable = Pageable.ofSize(size);
        Page<Diary> diaryPage = diaryRepository.findDiariesByCriteria(lastNo, accountId, startDate, orderByDesc, endDate, emotionTagIds, eventTagIds, pageable);
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
        return accountPerEventTagRepository.findByAccountId(accountId).map(AccountPerEventTag::getEventTags).orElseGet(() -> {
            AccountPerEventTag accountPerEventTag = new AccountPerEventTag();
            accountPerEventTag.setAccountId(accountId);
            accountPerEventTag.setEventTags(new ArrayList<>());
            accountPerEventTagRepository.save(accountPerEventTag);

            return accountPerEventTag.getEventTags();
        });
    }
}
