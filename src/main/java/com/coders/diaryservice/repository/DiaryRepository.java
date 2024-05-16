package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    List<Diary> findByAccountId(int accountId);
    Optional<Diary> findByDiaryNoAndAccountId(int diaryNo, int accountId);
}
