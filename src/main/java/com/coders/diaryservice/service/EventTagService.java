package com.coders.diaryservice.service;

import com.coders.diaryservice.dto.EventTagDto;
import com.coders.diaryservice.dto.mapper.DiaryMapper;
import com.coders.diaryservice.entity.AccountPerEventTag;
import com.coders.diaryservice.entity.EmotionTag;
import com.coders.diaryservice.entity.EventTag;
import com.coders.diaryservice.exception.eventTag.DuplicateEventTagUpdateException;
import com.coders.diaryservice.repository.AccountPerEventTagRepository;
import com.coders.diaryservice.repository.EventTagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.YearMonth;
import java.util.*;

@AllArgsConstructor
@Service
@Slf4j
public class EventTagService {

    private final EventTagRepository eventTagRepository;
    private final AccountPerEventTagRepository accountPerEventTagRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public EventTagDto createEventTagAndUpdateUser(String eventTag, Long accountId) {
        EventTag newEventTag = createEventTag(eventTag);
        updateAccountPerEventTag(newEventTag, accountId);
        return DiaryMapper.toEventTagDto(newEventTag);
    }


    public EventTag createEventTag(String eventTag) {
        try {
            return eventTagRepository.save(EventTag.builder().name(eventTag).build());
        } catch (DataIntegrityViolationException ex) {
            log.info("error", ex);
            entityManager.clear();
            return eventTagRepository.findByName(eventTag)
                    .orElseThrow(() -> new EntityNotFoundException("EventTag not found"));
        }
    }

    @Transactional
    public void updateAccountPerEventTag(EventTag eventTag, Long accountId) {
        AccountPerEventTag accountPerEventTag = accountPerEventTagRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("AccountPerEventTag not found"));
        List<EventTag> eventTags = accountPerEventTag.getEventTags();
        if (eventTags.contains(eventTag)) {
            throw new DuplicateEventTagUpdateException(String.format("이미 [{0}] 사건태그가 존재합니다.", eventTag.getName()));
        }
        eventTags.add(eventTag);
        accountPerEventTag.setEventTags(eventTags);
        accountPerEventTagRepository.save(accountPerEventTag);
    }

    public Map<EventTag,Integer> findByYearMonthForStatistics(Long accountId, YearMonth yearMonth, Integer emotionId) {
        List<EventTag> eventTags = eventTagRepository
                .findEventTagsWithDiaryNoByAccountIdAndYearMonth(accountId, yearMonth.toString(), emotionId);

        Map<EventTag, Integer> eventTagCountMap = new HashMap<>();

        for (EventTag eventTag : eventTags) {
            eventTagCountMap.put(eventTag, eventTagCountMap.getOrDefault(eventTag, 0) + 1);
        }

        return eventTagCountMap;
    }
}
