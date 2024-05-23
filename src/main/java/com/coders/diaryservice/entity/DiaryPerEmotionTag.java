package com.coders.diaryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@Table(name = "diary_per_emotion_tag")
@IdClass(DiaryPerEmotionTagId.class)
public class DiaryPerEmotionTag {
    @Id
    private int diaryNo;

    @Id
    private int emotionTagNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diaryNo", insertable = false, updatable = false)
    @JsonIgnore
    private Diary diary;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "emotionTagNo", insertable = false, updatable = false)
    @JsonIgnore
    private EmotionTag emotionTag;
}
