package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.EmotionTag;
import com.coders.diaryservice.repository.EmotionTagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmotionTagService {

    private final EmotionTagRepository emotionTagRepository;

    public EmotionTag findByOne(Long emotionTagNo, List<Long> emotionTagIds) {
        if (!emotionTagIds.contains(emotionTagNo)) {
            throw new IllegalArgumentException("Provided emotionTagNo is not in the emotionTagIds selected");
        }

        return emotionTagRepository.findEmotionTagByEmotionTagNo(emotionTagNo)
                .orElseThrow(() -> new EntityNotFoundException("EmotionTag not found"));
    }

}
