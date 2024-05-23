package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryNo;
    private Long account_id;
    private String content;
    private String coreEmotionTag;
    private Date dateCreated;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryPerEventTag> eventTags;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryPerEmotionTag> emotionTags;
}
