package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.EventTagWeight;
import com.coders.diaryservice.entity.EventTagWeightId;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventTagWeightRepository extends JpaRepository<EventTagWeight, EventTagWeightId> {

    @Query(value = "SELECT * FROM event_tag_weights " +
            "WHERE MATCH(word) AGAINST(:keyword IN BOOLEAN MODE) " +
            "ORDER BY weight DESC " +
            "LIMIT :limit",
            nativeQuery = true)
    List<EventTagWeight> searchByKeyword(@Param("keyword") String keyword, Integer limit);

}

