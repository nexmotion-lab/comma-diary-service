package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d WHERE (:lastNo IS NULL OR d.diaryNo < :lastNo) AND d.accountId = :accountId ORDER BY d.diaryNo DESC")
    Page<Diary> findByLastId(Long lastNo, Pageable pageable, Long accountId);
    Optional<Diary> findByDiaryNoAndAccountId(Long diaryNo, Long accountId);
}
