package com.coders.diaryservice.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class DiaryPerEventTagId implements Serializable {
    private Long diaryNo;
    private Long eventTagNo;

    // 기본 생성자
    public DiaryPerEventTagId() {}

    public DiaryPerEventTagId(Long diaryNo, Long eventTagNo) {
        this.diaryNo = diaryNo;
        this.eventTagNo = eventTagNo;
    }

    // hashCode 및 equals 메서드
    @Override
    public int hashCode() {
        return Objects.hash(diaryNo, eventTagNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DiaryPerEventTagId that = (DiaryPerEventTagId) obj;
        return Objects.equals(diaryNo, that.diaryNo) && Objects.equals(eventTagNo, that.eventTagNo);
    }

    // getters 및 setters
}

