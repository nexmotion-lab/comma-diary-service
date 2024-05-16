package com.coders.diaryservice.service;

import com.coders.diaryservice.entity.EventTag;
import com.coders.diaryservice.repository.EventTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventTagService {
    @Autowired
    private EventTagRepository eventTagRepository;

    public List<EventTag> getAllEventTags() {
        return eventTagRepository.findAll();
    }

    public Optional<EventTag> getEventTagById(int id) {
        return eventTagRepository.findById(id);
    }

    public EventTag saveEventTag(EventTag eventTag) {
        return eventTagRepository.save(eventTag);
    }

//    public void deleteEventTag(int id) {
//        eventTagRepository.deleteById(id);
//    }
}
