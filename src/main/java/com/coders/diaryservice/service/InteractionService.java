package com.coders.diaryservice.service;

import com.coders.diaryservice.dto.InteractionDto;
import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.entity.EmotionTag;
import com.coders.diaryservice.entity.InteractionText;
import com.coders.diaryservice.entity.Interactions;
import com.coders.diaryservice.repository.DiaryRepository;
import com.coders.diaryservice.repository.InteractionsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.aop.framework.AdvisedSupportListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InteractionService {

    private final InteractionsRepository interactionsRepository;
    private final DiaryRepository diaryRepository;

    public List<InteractionDto> getInteractions() {
        return interactionsRepository.findAll().stream().map(InteractionDto::new).toList();
    }

    @Transactional
    public void insertInteraction(String keyword, List<String> texts, byte[] image) {

        List<InteractionText> interactionTexts = texts.stream().map(text -> InteractionText.builder().text(text).build())
                .toList();

        Interactions interactions = Interactions.builder().keyword(keyword).image(image).texts(interactionTexts).build();

        interactionTexts.forEach(interactionText -> interactionText.setInteraction(interactions));
        interactionsRepository.save(interactions);

    }


    @Transactional
    public Interactions getInteractions(Long accountId) {
        List<Interactions> interactions = interactionsRepository.findAll();

        LocalDate startDateLocal = LocalDate.now().minusDays(7);
        Date startDate = Date.from(startDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (diaryRepository.countDiariesAfterDate(startDate, accountId) == 0) {
            List<Interactions> filteredInteractions = interactions.stream().filter(interaction -> interaction.getKeyword().equals("일기권유"))
                    .toList();
            int randomIndex = ThreadLocalRandom.current().nextInt(filteredInteractions.size());
            return filteredInteractions.get(randomIndex);
        } else {
            List<EmotionTag> emotionTags = diaryRepository.findTop10CoreEmotionByAccountId(accountId, PageRequest.of(0, 10));

            if (emotionTags.size() < 10) {
                int randomIndex = ThreadLocalRandom.current().nextInt(interactions.size());
                return interactions.get(randomIndex);
            } else {
                long emotionCount = emotionTags.stream().map(EmotionTag::getXvalue).filter(x -> x < 0).count();
                int randomInt = ThreadLocalRandom.current().nextInt(1, 11);

                if (emotionCount < randomInt) {
                    List<Interactions> filteredInteractions = interactions.stream()
                            .filter(interaction -> interaction.getKeyword().equals("파이팅") || interaction.getKeyword().equals("격려"))
                            .toList();
                    int randomIndex = ThreadLocalRandom.current().nextInt(filteredInteractions.size());
                    return filteredInteractions.get(randomIndex);
                } else {
                    int randomIndex = ThreadLocalRandom.current().nextInt(interactions.size());
                    return interactions.get(randomIndex);
                }
            }

        }
    }
}
