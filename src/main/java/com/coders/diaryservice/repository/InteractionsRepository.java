package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.Interactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InteractionsRepository extends JpaRepository<Interactions, Long> {


}
