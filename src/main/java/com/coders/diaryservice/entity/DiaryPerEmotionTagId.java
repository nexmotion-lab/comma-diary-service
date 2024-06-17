package com.coders.diaryservice.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DiaryPerEmotionTagId implements Serializable {
    private Long diaryNo;
    private Long emotionTagNo;

}
