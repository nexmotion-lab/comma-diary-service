package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.DiaryPerEmotionTag;
import com.coders.diaryservice.entity.DiaryPerEmotionTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryPerEmotionTagRepository extends JpaRepository<DiaryPerEmotionTag, DiaryPerEmotionTagId> {

    List<DiaryPerEmotionTag> findById_DiaryNo(Long diaryNo);

}
