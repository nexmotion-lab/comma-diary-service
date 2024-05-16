package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "diary_per_emotion_tag")
@IdClass(DiaryPerEmotionTagId.class)
public class DiaryPerEmotionTag {
    @Id
    private int diaryNo;

    @Id
    private int emotionTagNo;

    @ManyToOne
    @JoinColumn(name = "diaryNo", insertable = false, updatable = false)
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "emotionTagNo", insertable = false, updatable = false)
    private EmotionTag emotionTag;
}
