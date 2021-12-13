package com.sangam.iot.consumer.service.impl;

import com.sangam.iot.consumer.model.IotEvent;
import com.sangam.iot.consumer.repository.Event;
import com.sangam.iot.consumer.repository.EventRepository;
import com.sangam.iot.consumer.service.EventService;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void saveEvent(IotEvent iotEvent) {
        Event event = new Event();
        event.setSensorId(iotEvent.getId());
        event.setClusterId(Long.valueOf(iotEvent.getClusterId()));
        event.setName(iotEvent.getName());
        event.setType(iotEvent.getType());
        event.setValue(iotEvent.getValue());
        event.setTimestamp(iotEvent.getTimestamp());
        event.setInitialized(iotEvent.isInitialized());
        eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
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
        Optional<List<BigDecimal>> result =  eventRepository.findMedian(fromDate, toDate, eventType, clusterId);
        if(result.isPresent() && result.get() != null) {
            return result.get().stream().findFirst();
        }
        return Optional.empty();
    }
}
