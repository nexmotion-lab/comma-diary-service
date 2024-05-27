package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.DiaryPerEventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryPerEventTagRepository extends JpaRepository<DiaryPerEventTag, Long> {

    @Query("SELECT DISTINCT et.eventTagNo, et.name FROM DiaryPerEventTag det JOIN det.eventTag et JOIN det.diary d WHERE d.accountId = :accountId")
    List<Object[]> findDistinctEventTagsByAccountId(@Param("accountId") Long accountId);
}
