package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "event_tag_weights")
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventTagWeight {

    @EmbeddedId
    private EventTagWeightId id;

    @Column(name = "weight", nullable = false)
    private Float weight;

}
