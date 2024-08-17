package com.coders.diaryservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Table(name = "interactions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Interactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] image;
    private String keyword;

    @OneToMany(mappedBy = "interaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InteractionText> texts;


}
