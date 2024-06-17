package com.coders.diaryservice.repository;

import com.coders.diaryservice.entity.AccountPerEventTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountPerEventTagRepository extends JpaRepository<AccountPerEventTag, Long> {

    Optional<AccountPerEventTag> findByAccountId(Long id);
}
