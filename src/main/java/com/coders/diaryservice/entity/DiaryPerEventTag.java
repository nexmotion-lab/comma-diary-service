package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(DiaryPerEventTagId.class)
@Table(name = "diary_per_event_tag")
public class DiaryPerEventTag {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diaryNo")
    private Diary diary;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventTagNo")
    private EventTag eventTag;
}
