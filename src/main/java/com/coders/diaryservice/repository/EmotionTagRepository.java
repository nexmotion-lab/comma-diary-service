package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.EmotionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmotionTagRepository extends JpaRepository<EmotionTag, Integer> {
}
