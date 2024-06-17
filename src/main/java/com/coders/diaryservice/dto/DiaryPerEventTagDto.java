package com.coders.diaryservice.dto;

import lombok.Data;

@Data
public class DiaryPerEventTagDto {
    private Long diaryNo;
    private EventTagDto eventTag;
}
