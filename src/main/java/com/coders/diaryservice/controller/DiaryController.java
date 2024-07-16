package com.coders.diaryservice.controller;

import com.coders.diaryservice.dto.DiaryDto;
import com.coders.diaryservice.dto.DiaryRequest;
import com.coders.diaryservice.dto.EventTagDto;
import com.coders.diaryservice.dto.mapper.DiaryMapper;
import com.coders.diaryservice.entity.Diary;
import com.coders.diaryservice.entity.EventTag;
import com.coders.diaryservice.service.DiaryService;
import com.coders.diaryservice.service.EmotionTagService;
import com.coders.diaryservice.service.EventTagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/diary")
@AllArgsConstructor
@Slf4j
public class DiaryController {


    private final DiaryService diaryService;

    private final EmotionTagService emotionTagService;

    private final EventTagService eventTagService;


    @PostMapping()
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<Void> createDiary(@RequestBody DiaryRequest diaryRequest, HttpServletRequest request) {
        Long accountId = Long.parseLong(request.getHeader("X-User-Id"));

        LocalDateTime localDateTime = LocalDateTime.now();
        Date dateCreated = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());


        Diary diary = Diary.builder()
                        .accountId(accountId)
                        .content(diaryRequest.getContent())
                        .coreEmotionTag(emotionTagService.findByOne(diaryRequest.getCoreEmotionTagId(), diaryRequest.getEmotionTagIds()))
                        .dateCreated(dateCreated).build();
        diaryService.createDiary(diary, diaryRequest.getEventTagIds(), diaryRequest.getEmotionTagIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/eventTag")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<EventTagDto> createEventTags(@RequestParam String eventTagName, HttpServletRequest request) {
        EventTagDto newEventTag = eventTagService.createEventTagAndUpdateUser(eventTagName,
                Long.parseLong(request.getHeader("X-User-Id")));
        return ResponseEntity.ok(newEventTag);
    }

    @GetMapping()
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<List<DiaryDto>> getDiaries(
            @RequestParam(required = false) Long lastNo,
            @RequestParam(defaultValue = "10") int size, HttpServletRequest request,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) List<Long> emotionTagIds, @RequestParam(required = false) List<Long> eventTagIds,
            @RequestParam boolean orderByDesc) {
        List<DiaryDto> diaries = diaryService.getDiaries(lastNo, size, Long.parseLong(request.getHeader("X-User-Id")),
                startDate, endDate, emotionTagIds, eventTagIds, orderByDesc);
        return ResponseEntity.ok(diaries);
    }
    @DeleteMapping("/{diaryNo}")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public void deleteDiary(@PathVariable Long diaryNo, HttpServletRequest request) {
        String accountIdStr = request.getHeader("account_id");
        if (accountIdStr == null || accountIdStr.isEmpty()) {
            throw new IllegalArgumentException("Missing account_id header");
        }
        Long accountId = Long.parseLong(accountIdStr);
        diaryService.deleteDiary(accountId, diaryNo);
    }

    @GetMapping("/eventTag")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<List<EventTagDto>> getEventTags(HttpServletRequest request) {
        return ResponseEntity.ok(diaryService.getEventTagsByAccountId(Long.parseLong(request.getHeader("X-User-Id")))
                .stream().map(DiaryMapper::toEventTagDto).collect(Collectors.toList()));
    }


}
