package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Stack;

@Entity
@Data
@Table(name = "interaction_text")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InteractionText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interaction_id", nullable = false)
    private Interactions interaction;
    private String text;
}
