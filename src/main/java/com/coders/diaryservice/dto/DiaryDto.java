package com.coders.diaryservice.dto;

import com.coders.diaryservice.repository.EmotionTagDao;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DiaryDto {

    private Long diaryNo;
    private String content;
    private Date dateCreated;
    private EmotionTagDto coreEmotionTag;
    private List<EventTagDto> eventTags;
    private List<EmotionTagDto> emotionTags;
}
