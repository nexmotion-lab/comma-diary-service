package com.coders.diaryservice.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class DiaryPerEmotionTagId implements Serializable {
    private int diaryNo;
    private int emotionTagNo;

    // 기본 생성자
    public DiaryPerEmotionTagId() {}

    // 생성자
    public DiaryPerEmotionTagId(int diaryNo, int emotionTagNo) {
        this.diaryNo = diaryNo;
        this.emotionTagNo = emotionTagNo;
    }
}

