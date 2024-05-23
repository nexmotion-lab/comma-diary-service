package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.DiaryPerEventTag;
import com.coders.diaryservice.entity.DiaryPerEventTagId;
import com.coders.diaryservice.repository.DiaryPerEventTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;

@Service
public class DiaryPerEventTagService {
    @Autowired
    private DiaryPerEventTagRepository repository;

    public List<DiaryPerEventTag> findAll() {
        return repository.findAll();
    }

    public List<DiaryPerEventTag> findByDiaryNo(int diaryNo) {
        return repository.findByDiaryDiaryNo(diaryNo);
    }

    public Optional<DiaryPerEventTag> findById(DiaryPerEventTagId id) {
        return repository.findById(id);
    }

    public DiaryPerEventTag save(DiaryPerEventTag diaryPerEventTag) {
        return repository.save(diaryPerEventTag);
    }

    public void deleteById(DiaryPerEventTagId id) {
        repository.deleteById(id);
    }
}