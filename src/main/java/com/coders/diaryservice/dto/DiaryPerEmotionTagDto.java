package com.coders.diaryservice.dto;

import lombok.Data;

@Data
public class DiaryPerEmotionTagDto {
    private Long diaryNo;
    private EmotionTagDto emotionTag;
}
