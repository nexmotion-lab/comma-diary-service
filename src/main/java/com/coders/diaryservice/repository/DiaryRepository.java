package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByAccountId(Long account_id);
}
