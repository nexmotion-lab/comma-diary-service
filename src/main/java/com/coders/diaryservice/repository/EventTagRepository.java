package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.EmotionTag;
import com.coders.diaryservice.entity.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventTagRepository extends JpaRepository<EventTag, Long> {
    Optional<EventTag> findByName(String name);


    @Query(value = "SELECT et.* " +
            "FROM diary d " +
            "JOIN diary_per_event_tag det ON d.diary_no = det.diary_no " +
            "JOIN event_tag et ON det.event_tag_no = et.event_tag_no " +
            "WHERE d.account_id = :accountId " +
            "AND DATE_FORMAT(d.date_created, '%Y-%m') = :yearMonth",
            nativeQuery = true)
    List<EventTag> findEventTagsWithDiaryNoByAccountIdAndYearMonth(
            Long accountId, String yearMonth);



}
