package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByAccountId(Long account_id);
    Optional<Diary> findByDiaryNoAndAccountId(Long diaryNo, Long accountId);
}
