package com.coders.diaryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "emotion_tag")
public class EmotionTag {
    @Id
    private int emotion_tag_no;
    private String name;
    private int x_Value;
    private int y_Value;
}
