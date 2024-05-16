package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "event_tag")
public class EventTag {
    @Id
    private int eventTagNo;
    private String name;
}
