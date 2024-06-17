package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.EmotionTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmotionTagRepository extends JpaRepository<EmotionTag, Long> {

    Optional<EmotionTag> findEmotionTagByEmotionTagNo(Long emotionTagNo);

}
