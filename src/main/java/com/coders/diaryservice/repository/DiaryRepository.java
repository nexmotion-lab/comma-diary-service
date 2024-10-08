package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.entity.EmotionTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DynamicDiaryRepository{

    @Query("SELECT d.coreEmotionTag FROM Diary d WHERE d.accountId = :accountId ORDER BY d.diaryNo DESC")
    List<EmotionTag> findTop10CoreEmotionByAccountId(Long accountId, Pageable pageable);
    
    Optional<Diary> findByDiaryNoAndAccountId(Long diaryNo, Long accountId);

    @Query("SELECT COUNT(d) " +
            "FROM Diary d " +
            "JOIN d.eventTags det " +
            "WHERE d.accountId = :accountId " +
            "AND det.id.eventTagNo = :eventTagNo")
    Long countByAccountIdAndEventTagNo(Long accountId, Long eventTagNo);

    @Query("SELECT d FROM Diary d " +
            "WHERE d.dateCreated BETWEEN :startDate AND :endDate " +
            "AND d.accountId = :accountId")
    List<Diary> findDiariesWithinDateRange(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("accountId") Long accountId);

    @Query("SELECT COUNT(d) FROM Diary d " +
            "WHERE d.dateCreated > :sevenDaysAgo " +
            "AND d.accountId = :accountId")
    long countDiariesAfterDate(
            @Param("sevenDaysAgo") Date sevenDaysAgo,
            @Param("accountId") Long accountId);


    @Query("SELECT d FROM Diary d WHERE FUNCTION('DATE', d.dateCreated) = :date AND d.accountId = :id")
    Optional<Diary> findByDateCreated(LocalDate date, Long id);
}
