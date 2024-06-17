package com.coders.diaryservice.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class DiaryPerEventTagId implements Serializable {

    private Long diaryNo;
    private Long eventTagNo;


}

