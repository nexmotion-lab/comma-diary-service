package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface DynamicDiaryRepository {

    Page<Diary> findDiariesByCriteria(Long lastNo, Long accountId, Date startDate, Date endDate, List<Long> emotionTagIds, List<Long> eventTagIds, Pageable pageable);
}
