package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.DiaryPerEmotionTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryPerEmotionTagRepository extends JpaRepository<DiaryPerEmotionTag, Long> {
}
