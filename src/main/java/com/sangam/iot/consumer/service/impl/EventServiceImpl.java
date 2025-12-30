package com.sangam.iot.consumer.service.impl;

import com.sangam.iot.consumer.model.IotEvent;
import com.sangam.iot.consumer.repository.Event;
import com.sangam.iot.consumer.repository.EventRepository;
import com.sangam.iot.consumer.service.EventService;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public void saveEvent(IotEvent iotEvent) {
        Event event = new Event();
        event.setSensorId(iotEvent.getId());
        event.setClusterId(Long.parseLong(iotEvent.getClusterId()));
        event.setName(iotEvent.getName());
        event.setType(iotEvent.getType());
        event.setValue(iotEvent.getValue());
        event.setTimestamp(iotEvent.getTimestamp());
        event.setInitialized(iotEvent.isInitialized());
        eventRepository.save(event);
    }

    @Override
    public List<IotEvent> getAllEvents() {
        return eventRepository.findAll()
                .stream().map( event -> new IotEvent(
                        (int) event.getId(), event.getType(), event.getName(), String.valueOf(event.getClusterId()), event.getTimestamp(), event.getValue(), true
                )
                ).collect(toList());
    }

    @Override
    public Optional<BigDecimal> findAverage(OffsetDateTime fromDate, OffsetDateTime toDate,
            String eventType,
            Long clusterId) {
        return eventRepository.findAverage(fromDate, toDate, eventType, clusterId);
    }

    @Override
    public Optional<BigDecimal> findMax(OffsetDateTime fromDate, OffsetDateTime toDate,
            String eventType,
            Long clusterId) {
        return eventRepository.findMax(fromDate, toDate, eventType, clusterId);
    }

    @Override
    public Optional<BigDecimal> findMin(OffsetDateTime fromDate, OffsetDateTime toDate,
            String eventType,
            Long clusterId) {
        return eventRepository.findMin(fromDate, toDate, eventType, clusterId);
    }

    @Override
    public Optional<BigDecimal> findMedian(OffsetDateTime fromDate, OffsetDateTime toDate,
            String eventType,
            Long clusterId) {
        return eventRepository.findMedian(fromDate, toDate, eventType, clusterId);
    }
}
