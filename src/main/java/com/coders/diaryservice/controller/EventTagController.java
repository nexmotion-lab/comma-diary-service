package com.coders.diaryservice.controller;

import com.coders.diaryservice.entity.EventTag;
import com.coders.diaryservice.service.EventTagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "eventTag API", description = "감정태그 API 입니다.")
@RestController
@RequestMapping("/api/v1/event")
public class EventTagController {
    @Autowired
    private EventTagService eventTagService;

    @GetMapping
    public ResponseEntity<List<EventTag>> getAllEventTags() {
        List<EventTag> tags = eventTagService.getAllEventTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTag> getEventTagById(@PathVariable("id") int id) {
        return eventTagService.getEventTagById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventTag> createEventTag(@RequestBody EventTag eventTag) {
        EventTag newTag = eventTagService.saveEventTag(eventTag);
        return ResponseEntity.ok(newTag);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<EventTag> updateEventTag(@PathVariable("id") int id, @RequestBody EventTag eventTag) {
//        return eventTagService.getEventTagById(id)
//                .map(existingTag -> {
//                    existingTag.setName(eventTag.getName());
//                    EventTag updatedTag = eventTagService.saveEventTag(existingTag);
//                    return ResponseEntity.ok(updatedTag);
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteEventTag(@PathVariable("id") int id) {
//        return eventTagService.getEventTagById(id)
//                .map(tag -> {
//                    eventTagService.deleteEventTag(tag.getEventTagNo());
//                    return ResponseEntity.ok().build();
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
}
