package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "diary_per_event_tag")
public class DiaryPerEventTag {
    @Id
    @ManyToOne
    @JoinColumn(name = "diaryNo")
    private Diary diary;

    @Id
    @ManyToOne
    @JoinColumn(name = "eventTagNo")
    private EventTag eventTag;
}
