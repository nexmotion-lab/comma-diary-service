package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.entity.DiaryPerEmotionTag;
import com.coders.diaryservice.entity.DiaryPerEventTag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@AllArgsConstructor
public class DynamicDiaryRepositoryImpl implements DynamicDiaryRepository{

    private final EntityManager entityManager;

    @Override
    public Page<Diary> findDiariesByCriteria(Long lastNo, Long accountId, LocalDate startDate, boolean orderByDesc,
                                             LocalDate endDate, List<Long> emotionTagIds, List<Long> eventTagIds, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Diary> query = cb.createQuery(Diary.class);
        Root<Diary> diary = query.from(Diary.class);

        List<Predicate> predicates = new ArrayList<>();

        if (lastNo != null && orderByDesc) {
            predicates.add(cb.lessThan(diary.get("diaryNo"), lastNo));
        } else if (lastNo != null && !orderByDesc) {
            predicates.add(cb.greaterThan(diary.get("diaryNo"), lastNo));
        }
        if (accountId != null) {
            predicates.add(cb.equal(diary.get("accountId"), accountId));
        }

        if (startDate != null && endDate != null) {
            predicates.add(cb.between(cb.function("DATE", LocalDate.class, diary.get("dateCreated")), startDate, endDate));
        }

        if (emotionTagIds != null && !emotionTagIds.isEmpty()) {
            Join<Diary, DiaryPerEmotionTag> emotionTags = diary.join("emotionTags");
            predicates.add(emotionTags.get("emotionTag").get("emotionTagNo").in(emotionTagIds));
        }

        if (eventTagIds != null && !eventTagIds.isEmpty()) {
            Join<Diary, DiaryPerEventTag> eventTags = diary.join("eventTags");
            predicates.add(eventTags.get("eventTag").get("eventTagNo").in(eventTagIds));
        }

        query.select(diary)
                .where(predicates.toArray(new Predicate[0]));

        if (orderByDesc) {
            query.orderBy(cb.desc(diary.get("diaryNo")));
        } else {
            query.orderBy(cb.asc(diary.get("diaryNo")));
        }

        List<Diary> resultList = entityManager.createQuery(query)
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(resultList);
    }
}
