package com.sangam.iot.consumer.service;

import com.sangam.iot.consumer.model.IotEvent;
import com.sangam.iot.consumer.repository.Event;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {

    void saveEvent(IotEvent iotEvent);

    List<Event> getAllEvents();

    Optional<BigDecimal> findAverage(OffsetDateTime fromDate, OffsetDateTime toDate, String eventType,
            Long clusterId);

    Optional<BigDecimal> findMax(OffsetDateTime fromDate, OffsetDateTime toDate, String eventType,
            Long clusterId);

    Optional<BigDecimal> findMin(OffsetDateTime fromDate, OffsetDateTime toDate, String eventType,
            Long clusterId);

    Optional<BigDecimal> findMedian(OffsetDateTime fromDate, OffsetDateTime toDate, String eventType,
            Long clusterId);

}
