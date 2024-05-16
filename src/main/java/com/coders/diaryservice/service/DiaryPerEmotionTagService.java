package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.DiaryPerEmotionTag;
import com.coders.diaryservice.entity.DiaryPerEmotionTagId;
import com.coders.diaryservice.repository.DiaryPerEmotionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryPerEmotionTagService {

    @Autowired
    private DiaryPerEmotionTagRepository repository;

    public DiaryPerEmotionTag save(DiaryPerEmotionTag diaryPerEmotionTag) {
        return repository.save(diaryPerEmotionTag);
    }

    public List<DiaryPerEmotionTag> findAll() {
        return repository.findAll();
    }

    public List<DiaryPerEmotionTag> findByDiaryNo(int diaryNo) {
        return repository.findByDiaryNo(diaryNo);
    }

    public void delete(DiaryPerEmotionTagId id) {
        repository.deleteById(id);
    }
}

