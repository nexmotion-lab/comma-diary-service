package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.entity.EmotionTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DynamicDiaryRepository{

    @Query("SELECT d FROM Diary d WHERE (:lastNo IS NULL OR d.diaryNo < :lastNo) AND d.accountId = :accountId ORDER BY d.diaryNo DESC")
    Page<Diary> findByLastId(Long lastNo, Pageable pageable, Long accountId);
    
    Optional<Diary> findByDiaryNoAndAccountId(Long diaryNo, Long accountId);

    @Query("SELECT d FROM Diary d " +
            "WHERE d.dateCreated BETWEEN :startDate AND :endDate " +
            "AND d.accountId = :accountId")
    List<Diary> findDiariesWithinDateRange(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("accountId") Long accountId);


    @Query("SELECT d FROM Diary d WHERE FUNCTION('DATE', d.dateCreated) = :date")
    Optional<Diary> findByDateCreated(LocalDate date);
}
