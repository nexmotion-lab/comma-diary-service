package com.coders.diaryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "diary")
public class Diary {

    @Id
    private int diaryNo;

    private int accountId;
    private String content;
    private String coreEmotionTag;
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "diary")
    @JsonIgnoreProperties("diary")  // 순환 참조 방지
    private List<DiaryPerEventTag> diaryPerEventTags;

    @OneToMany(mappedBy = "diary")
    @JsonIgnoreProperties("diary")  // 순환 참조 방지
    private List<DiaryPerEmotionTag> diaryPerEmotionTags;
}
