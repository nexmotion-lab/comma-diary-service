package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "emotion_tag")
public class EmotionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emotionTagNo;
    private String name;
    private int xvalue;
    private int yvalue;

    // getters and setters
}
