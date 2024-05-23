package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.EmotionTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionTagRepository extends JpaRepository<EmotionTag, Long> {

}
