package com.coders.diaryservice.dto;


import com.coders.diaryservice.entity.InteractionText;
import com.coders.diaryservice.entity.Interactions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteractionDto {

    private Long id;
    private String image;
    private String keyword;
    private List<String> texts;

    public InteractionDto(Interactions interactions) {
        this.id = interactions.getId();
        this.image = Base64.getEncoder().encodeToString(interactions.getImage());
        this.keyword = interactions.getKeyword();
        this.texts = interactions.getTexts().stream().map(InteractionText::getText).toList();
    }
}
