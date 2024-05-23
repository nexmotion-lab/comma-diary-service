package com.coders.diaryservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiaryRequest {
    private String content;
    private String coreEmotionTag;
    private List<Long> eventTagIds;
    private List<Long> emotionTagIds;

    // getters and setters
}
