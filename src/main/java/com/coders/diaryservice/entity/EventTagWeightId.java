package com.coders.diaryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class EventTagWeightId implements Serializable {


    @Column(name = "event_tag_no", nullable = false)
    private Long eventTagNo;

    @Column(name = "word", nullable = false)
    private String word;

}
