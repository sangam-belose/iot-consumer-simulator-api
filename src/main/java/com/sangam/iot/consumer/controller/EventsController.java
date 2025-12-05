package com.sangam.iot.consumer.controller;

import com.sangam.iot.consumer.repository.Event;
import com.sangam.iot.consumer.service.EventService;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/events")
@Slf4j
public class EventsController {

    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/average")
    public ResponseEntity<BigDecimal> getAverage(
            @RequestParam("from") @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fromDate,
            @RequestParam("to") @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime toDate,
            @RequestParam(value = "type", required = false) String eventType,
            @RequestParam(value = "clusterId", required = false) Long clusterId) {
        Optional<BigDecimal> result = eventService.findAverage(fromDate, toDate, eventType,
                clusterId);

        if (result.isEmpty()) {
            return noContent().build();
        }
        log.info("avgResult: {}", result.get());
        return ok(result.get());
    }

    @GetMapping("/max")
    public ResponseEntity<BigDecimal> getMax(
            @RequestParam("from") @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fromDate,
            @RequestParam("to") @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime toDate,
            @RequestParam(value = "type", required = false) String eventType,
            @RequestParam(value = "clusterId", required = false) Long clusterId) {
        Optional<BigDecimal> result = eventService.findMax(fromDate, toDate, eventType, clusterId);

        if (result.isEmpty()) {
            return noContent().build();
        }
        log.info("maxResult: {}", result.get());
        return ok(result.get());
    }

    @GetMapping("/min")
    public ResponseEntity<BigDecimal> getMin(
            @RequestParam("from") @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fromDate,
            @RequestParam("to") @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime toDate,
            @RequestParam(value = "type", required = false) String eventType,
            @RequestParam(value = "clusterId", required = false) Long clusterId) {
        Optional<BigDecimal> result = eventService.findMin(fromDate, toDate, eventType, clusterId);

        if (result.isEmpty()) {
            return noContent().build();
        }
        log.info("minResult: {}", result.get());
        return ok(result.get());
    }

    @GetMapping("/median")
    public ResponseEntity<BigDecimal> getMedian(
            @RequestParam("from") @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fromDate,
            @RequestParam("to") @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime toDate,
            @RequestParam(value = "type", required = false) String eventType,
            @RequestParam(value = "clusterId", required = false) Long clusterId) {
        Optional<BigDecimal> result = eventService.findMedian(fromDate, toDate, eventType,
                clusterId);

        if (result.isEmpty()) {
            return noContent().build();
        }
        log.info("medianResult: {}", result.get());
        return ok(result.get());
    }

}
