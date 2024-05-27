package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "diary_per_emotion_tag")
public class DiaryPerEmotionTag {
    @EmbeddedId
    private DiaryPerEmotionTagId id;

    @ManyToOne
    @MapsId("diaryNo")
    @JoinColumn(name = "diary_no")
    private Diary diary;

    @ManyToOne
    @MapsId("emotionTagNo")
    @JoinColumn(name = "emotion_tag_no")
    private EmotionTag emotionTag;

    // 기본 생성자
    public DiaryPerEmotionTag() {}

    public DiaryPerEmotionTag(DiaryPerEmotionTagId id, Diary diary, EmotionTag emotionTag) {
        this.id = id;
        this.diary = diary;
        this.emotionTag = emotionTag;
    }
}
