package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.DiaryPerEventTag;
import com.coders.diaryservice.entity.DiaryPerEventTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryPerEventTagRepository extends JpaRepository<DiaryPerEventTag, DiaryPerEventTagId> {
    List<DiaryPerEventTag> findByDiaryDiaryNo(int diaryNo);
}