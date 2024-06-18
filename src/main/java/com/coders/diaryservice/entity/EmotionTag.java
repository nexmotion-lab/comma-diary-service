package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "emotion_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmotionTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long emotionTagNo;

    @OneToMany(mappedBy = "emotionTag", cascade = CascadeType.ALL)
    private List<DiaryPerEmotionTag> diaryPerEmotionTags = new ArrayList<>();
    @OneToMany(mappedBy = "coreEmotionTag", cascade = CascadeType.ALL)
    private List<Diary> diaries = new ArrayList<>();

    private String name;
    private int xvalue;
    private int yvalue;

}
