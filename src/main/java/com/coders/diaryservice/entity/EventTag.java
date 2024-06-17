package com.coders.diaryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "event_tag")
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventTagNo;

    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "eventTag", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DiaryPerEventTag> diaryPerEventTags = new ArrayList<>();
}
