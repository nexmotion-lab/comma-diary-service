package com.coders.diaryservice.dto.mapper;

import com.coders.diaryservice.dto.*;
import com.coders.diaryservice.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DiaryMapper {

    public static DiaryDto toDto(Diary diary) {
        if (diary == null) return null;

        DiaryDto diaryDto = new DiaryDto();
        diaryDto.setDiaryNo(diary.getDiaryNo());
        diaryDto.setContent(diary.getContent());
        diaryDto.setDateCreated(diary.getDateCreated());
        diaryDto.setCoreEmotionTag(toEmotionTagDto(diary.getCoreEmotionTag()));
        diaryDto.setEventTags(diary.getEventTags().stream().map(DiaryMapper::toDiaryPerEventTagDto)
                .map(DiaryPerEventTagDto::getEventTag).collect(Collectors.toList()));
        diaryDto.setEmotionTags(diary.getEmotionTags().stream().map(DiaryMapper::toDiaryPerEmotionTagDto)
                .map(DiaryPerEmotionTagDto::getEmotionTag).collect(Collectors.toList()));

        return diaryDto;
    }

    public static EmotionTagDto toEmotionTagDto(EmotionTag emotionTag) {
        if (emotionTag == null) return null;

        EmotionTagDto dto = new EmotionTagDto();
        dto.setEmotionTagNo(emotionTag.getEmotionTagNo());
        dto.setName(emotionTag.getName());

        return dto;
    }

    private static DiaryPerEventTagDto toDiaryPerEventTagDto(DiaryPerEventTag diaryPerEventTag) {
        if (diaryPerEventTag == null) return null;

        DiaryPerEventTagDto dto = new DiaryPerEventTagDto();
        dto.setDiaryNo(diaryPerEventTag.getDiary().getDiaryNo());
        dto.setEventTag(toEventTagDto(diaryPerEventTag.getEventTag()));

        return dto;
    }

    private static DiaryPerEmotionTagDto toDiaryPerEmotionTagDto(DiaryPerEmotionTag diaryPerEmotionTag) {
        if (diaryPerEmotionTag == null) return null;

        DiaryPerEmotionTagDto dto = new DiaryPerEmotionTagDto();
        dto.setDiaryNo(diaryPerEmotionTag.getDiary().getDiaryNo());
        dto.setEmotionTag(toEmotionTagDto(diaryPerEmotionTag.getEmotionTag()));

        return dto;
    }

    public static EventTagDto toEventTagDto(EventTag eventTag) {
        if (eventTag == null) return null;

        EventTagDto dto = new EventTagDto();
        dto.setEventTagNo(eventTag.getEventTagNo());
        dto.setName(eventTag.getName());

        return dto;
    }
}
