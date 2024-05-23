package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.DiaryPerEmotionTag;
import com.coders.diaryservice.entity.DiaryPerEmotionTagId;
import com.coders.diaryservice.repository.DiaryPerEmotionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryPerEmotionTagService {

    @Autowired
    private DiaryPerEmotionTagRepository repository;

    public List<DiaryPerEmotionTag> findAll() {
        return repository.findAll();
    }

    public Optional<DiaryPerEmotionTag> findById(DiaryPerEmotionTagId id) {
        return repository.findById(id);
    }

    public DiaryPerEmotionTag save(DiaryPerEmotionTag diaryPerEmotionTag) {
        return repository.save(diaryPerEmotionTag);
    }

    public void deleteById(DiaryPerEmotionTagId id) {
        repository.deleteById(id);
    }
    public List<DiaryPerEmotionTag> findByDiaryNo(int diaryNo) {
        return repository.findByDiaryNo(diaryNo);
    }
}
