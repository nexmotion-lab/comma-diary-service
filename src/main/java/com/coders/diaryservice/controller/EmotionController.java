package com.coders.diaryservice.controller;

import com.coders.diaryservice.dto.EmotionTagDto;
import com.coders.diaryservice.dto.EventTagDto;
import com.coders.diaryservice.dto.mapper.DiaryMapper;
import com.coders.diaryservice.entity.EmotionTag;
import com.coders.diaryservice.entity.EventTag;
import com.coders.diaryservice.service.EmotionTagService;
import com.coders.diaryservice.service.EventTagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EmotionController {

    private final EmotionTagService emotionTagService;
    private final EventTagService eventTagService;

    @GetMapping("/statistics/emotion")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<Map<EmotionTagDto, Integer>> getEmotionTagsForStatistics(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
                                                                      HttpServletRequest request) {
        Long userId = Long.parseLong(request.getHeader("X-User-Id"));

        Map<EmotionTag, Integer> emotionTagCountMap = emotionTagService.findByYearMonthForStatistics(userId, yearMonth);

        Map<EmotionTagDto, Integer> responseMap = emotionTagCountMap.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        DiaryMapper.toEmotionTagDto(entry.getKey()),
                        entry.getValue()
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/calendar")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<Map<Date, String>> getEmotionTagsForCalendar(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
                                                                       HttpServletRequest request) {
        Map<Date, String> calendarEmotionTags = emotionTagService.findByDateForCalendar(
                Long.parseLong(request.getHeader("X-User-Id")), yearMonth);

        return ResponseEntity.ok(calendarEmotionTags);
    }

    @GetMapping("/statistics/event")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<Map<EventTagDto, Integer>> getEventTagsForStatistics(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
                                                                                   HttpServletRequest request) {
        Long userId = Long.parseLong(request.getHeader("X-User-Id"));

        Map<EventTag, Integer> eventTagCountMap = eventTagService.findByYearMonthForStatistics(userId, yearMonth);

        Map<EventTagDto, Integer> responseMap = eventTagCountMap.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        DiaryMapper.toEventTagDto(entry.getKey()),
                        entry.getValue()
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return ResponseEntity.ok(responseMap);
    }
}
