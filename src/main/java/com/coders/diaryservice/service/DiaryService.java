package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    public Diary saveDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    public List<Diary> findDiariesByAccountId(int accountId) {
        return diaryRepository.findByAccountId(accountId);
    }

    public Optional<Diary> findDiaryByIdAndAccountId(int diaryNo, int accountId) {
        return diaryRepository.findByDiaryNoAndAccountId(diaryNo, accountId);
    }
    public Diary updateDiary(int id, Diary diaryDetails) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diary not found with id " + id));
        // todo::
        diary.setAccountId(diary.getAccountId());
        diary.setContent(diary.getContent());

        return diaryRepository.save(diary);
    }

    public void deleteDiary(int id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diary not found with id " + id));
        diaryRepository.delete(diary);
    }
}
