package com.coders.diaryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRequest {
    private Long account_id;
    private String content;
    private String coreEmotionTag;
    private List<Long> eventTagIds;
    private List<Long> emotionTagIds;

    // getters and setters
}

