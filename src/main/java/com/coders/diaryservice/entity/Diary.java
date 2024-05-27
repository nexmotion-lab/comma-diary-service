package com.coders.diaryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long accountId;
    private String content;
    private String coreEmotionTag;
    private Date dateCreated;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("diary")  // 순환 참조 방지
    private List<DiaryPerEventTag> eventTags;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("diary")  // 순환 참조 방지
    private List<DiaryPerEmotionTag> emotionTags;
}
