package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.EventTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTagRepository extends JpaRepository<EventTag, Long> {
    Optional<EventTag> findByName(String name);



}
