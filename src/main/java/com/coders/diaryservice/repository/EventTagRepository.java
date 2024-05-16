package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTagRepository extends JpaRepository<EventTag, Integer> {
}
