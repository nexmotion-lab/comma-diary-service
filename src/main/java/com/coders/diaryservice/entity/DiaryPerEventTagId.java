package com.coders.diaryservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class DiaryPerEventTagId implements Serializable {
    private int diary;
    private int eventTag;

    public DiaryPerEventTagId() {}

    public DiaryPerEventTagId(int diary, int eventTag) {
        this.diary = diary;
        this.eventTag = eventTag;
    }
}
