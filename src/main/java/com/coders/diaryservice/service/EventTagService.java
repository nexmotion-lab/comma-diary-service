package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.AccountPerEventTag;
import com.coders.diaryservice.entity.EventTag;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class EventTagService {

    private final EventTagRepository eventTagRepository;
    private final AccountPerEventTagRepository accountPerEventTagRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void createEventTagAndUpdateUser(String eventTag, Long accountId) {
        EventTag newEventTag = createEventTag(eventTag);
        updateAccountPerEventTag(newEventTag, accountId);
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
            throw new IllegalArgumentException("EventTag already exists for the account");
        }
        eventTags.add(eventTag);
        accountPerEventTag.setEventTags(eventTags);
        accountPerEventTagRepository.save(accountPerEventTag);
    }
}
