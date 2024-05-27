package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "diary_per_event_tag")
public class DiaryPerEventTag {
    @EmbeddedId
    private DiaryPerEventTagId id;

    @ManyToOne
    @MapsId("diaryNo")
    @JoinColumn(name = "diary_no")
    private Diary diary;

    @ManyToOne
    @MapsId("eventTagNo")
    @JoinColumn(name = "event_tag_no")
    private EventTag eventTag;

    // 기본 생성자
    public DiaryPerEventTag() {}

    public DiaryPerEventTag(DiaryPerEventTagId id, Diary diary, EventTag eventTag) {
        this.id = id;
        this.diary = diary;
        this.eventTag = eventTag;
    }
}
