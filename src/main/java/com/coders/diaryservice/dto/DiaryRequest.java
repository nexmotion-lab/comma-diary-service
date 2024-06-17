package com.coders.diaryservice.dto;

import com.coders.diaryservice.entity.EventTag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class DiaryRequest {

    private Long account_id;
    @NotNull
    @Size(max = 500)
    private String content;
    @NotNull
    private Long coreEmotionTagId;
    @NotNull
    private List<Long> eventTagIds;
    @NotNull
    private List<Long> emotionTagIds;
}

