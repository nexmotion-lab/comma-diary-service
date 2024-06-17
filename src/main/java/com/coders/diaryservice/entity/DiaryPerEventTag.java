package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(name = "diary_per_event_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DiaryPerEventTag implements Persistable<DiaryPerEventTagId> {

    @EmbeddedId
    private DiaryPerEventTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("diaryNo")
    @JoinColumn(name = "diary_no")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventTagNo")
    @JoinColumn(name = "event_tag_no")
    private EventTag eventTag;

    @CreatedDate
    @Transient
    private LocalDate created;



    @Override
    public DiaryPerEventTagId getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return created == null;
    }
}
