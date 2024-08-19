package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.DiaryPerEmotionTagId;
import com.coders.diaryservice.entity.DiaryPerEventTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryPerEventTagRepository extends JpaRepository<DiaryPerEventTag, DiaryPerEmotionTagId> {
}
