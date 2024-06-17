package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(name = "diary_per_emotion_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class DiaryPerEmotionTag implements Persistable<DiaryPerEmotionTagId> {

    @EmbeddedId
    private DiaryPerEmotionTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("diaryNo")
    @JoinColumn(name = "diary_no")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("emotionTagNo")
    @JoinColumn(name = "emotion_tag_no")
    private EmotionTag emotionTag;

    @CreatedDate
    @Transient
    private LocalDate created;



    @Override
    public DiaryPerEmotionTagId getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return created == null;
    }
}
