package com.coders.diaryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryNo;

    private Long accountId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "core_emotion_tag")
    private EmotionTag coreEmotionTag;

    private Date dateCreated;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryPerEventTag> eventTags = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryPerEmotionTag> emotionTags = new ArrayList<>();
}
