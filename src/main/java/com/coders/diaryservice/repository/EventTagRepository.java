package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTagRepository extends JpaRepository<EventTag, Long> {
    EventTag findByName(String name);
}
