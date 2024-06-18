package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.EmotionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmotionTagRepository extends JpaRepository<EmotionTag, Long> {

    Optional<EmotionTag> findEmotionTagByEmotionTagNo(Long emotionTagNo);

    @Query(value = "SELECT et.* " +
            "FROM diary d " +
            "JOIN diary_per_emotion_tag det ON d.diary_no = det.diary_no " +
            "JOIN emotion_tag et ON det.emotion_tag_no = et.emotion_tag_no " +
            "WHERE d.account_id = :accountId " +
            "AND DATE_FORMAT(d.date_created, '%Y-%m') = :yearMonth",
            nativeQuery = true)
    List<EmotionTag> findEmotionTagsWithDiaryNoByAccountIdAndYearMonth(
            Long accountId, String yearMonth);


}
