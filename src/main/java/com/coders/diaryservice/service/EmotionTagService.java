package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.EmotionTag;
import com.coders.diaryservice.repository.EmotionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmotionTagService {

    @Autowired
    private EmotionTagRepository emotionTagRepository;

    public List<EmotionTag> getAllEmotionTags() {
        return emotionTagRepository.findAll();
    }

    public Optional<EmotionTag> getEmotionTagById(int id) {
        return emotionTagRepository.findById(id);
    }
}
