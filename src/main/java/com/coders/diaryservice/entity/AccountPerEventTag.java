package com.coders.diaryservice.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Table(name = "account_event_tags")
@NoArgsConstructor()
public class AccountPerEventTag implements Persistable<Long> {

    @Id
    @Setter
    private Long accountId;

    private String eventTags;

    public void setEventTags(List<EventTag> eventTags) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.eventTags = objectMapper.writeValueAsString(eventTags);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting event tags to JSON", e);
        }
    }

    public List<EventTag> getEventTags() {
        if (this.eventTags == null || this.eventTags.isEmpty()) {
            return new ArrayList<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(this.eventTags, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to event tags", e);
        }
    }

    @Override
    public Long getId() {
        return this.accountId;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
