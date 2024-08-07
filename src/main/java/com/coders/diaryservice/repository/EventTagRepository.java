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
            "FROM (SELECT d.diary_no " +
            "      FROM diary d " +
            "      WHERE d.account_id = :accountId " +
            "        AND DATE_FORMAT(d.date_created, '%Y-%m') = :yearMonth) AS filtered_diaries " +
            "JOIN diary_per_emotion_tag demt ON demt.diary_no = filtered_diaries.diary_no " +
            "JOIN diary_per_event_tag det ON filtered_diaries.diary_no = det.diary_no " +
            "JOIN event_tag et ON det.event_tag_no = et.event_tag_no " +
            "WHERE demt.emotion_tag_no = :emotionId",
            nativeQuery = true)
    List<EventTag> findEventTagsWithDiaryNoByAccountIdAndYearMonth(
            Long accountId, String yearMonth, Integer emotionId);



}
