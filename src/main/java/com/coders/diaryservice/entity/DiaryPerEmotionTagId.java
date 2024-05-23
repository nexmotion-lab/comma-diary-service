package com.coders.diaryservice.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class DiaryPerEmotionTagId implements Serializable {
    private Long diaryNo;
    private Long emotionTagNo;

    // 기본 생성자
    public DiaryPerEmotionTagId() {}

    public DiaryPerEmotionTagId(Long diaryNo, Long emotionTagNo) {
        this.diaryNo = diaryNo;
        this.emotionTagNo = emotionTagNo;
    }

    // hashCode 및 equals 메서드
    @Override
    public int hashCode() {
        return Objects.hash(diaryNo, emotionTagNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DiaryPerEmotionTagId that = (DiaryPerEmotionTagId) obj;
        return Objects.equals(diaryNo, that.diaryNo) && Objects.equals(emotionTagNo, that.emotionTagNo);
    }

    // getters 및 setters
}
