package com.sangam.iot.consumer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sangam.iot.consumer.repository.EventRepository;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import com.sangam.iot.consumer.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    private static final String TEMP_EVENT_TYPE = "TEMPERATURE";
    private static final long CLUSTER_ID = 1L;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    public void findAverage_successfully() {
        when(eventRepository.findAverage(any(OffsetDateTime.class), any(OffsetDateTime.class),
                anyString(), anyLong())).thenReturn(Optional.of(
                BigDecimal.TEN));
        Optional<BigDecimal> result = eventService.findAverage(OffsetDateTime.now(),
                OffsetDateTime.MIN, TEMP_EVENT_TYPE, CLUSTER_ID);
        assertEquals(BigDecimal.TEN, result.get());
        verify(eventRepository, times(1)).findAverage(any(), any(), anyString(), anyLong());
    }

    @Test
    public void findMax_successfully() {
        when(eventRepository.findMax(any(OffsetDateTime.class), any(OffsetDateTime.class), anyString(),
                anyLong())).thenReturn(Optional.of(
                BigDecimal.ONE));
        Optional<BigDecimal> result = eventService.findMax(OffsetDateTime.now(), OffsetDateTime.MAX,
                TEMP_EVENT_TYPE, CLUSTER_ID);
        assertEquals(BigDecimal.ONE, result.get());
        verify(eventRepository, times(1)).findMax(any(), any(), anyString(), anyLong());
    }

    @Test
    public void findMin_successfully() {
        when(eventRepository.findMin(any(OffsetDateTime.class), any(OffsetDateTime.class), anyString(),
                anyLong())).thenReturn(Optional.of(
                BigDecimal.ZERO));
        Optional<BigDecimal> result = eventService.findMin(OffsetDateTime.now(), OffsetDateTime.MIN,
                TEMP_EVENT_TYPE, CLUSTER_ID);
        assertEquals(BigDecimal.ZERO, result.get());
        verify(eventRepository, times(1)).findMin(any(), any(), anyString(), anyLong());
    }

    @Test
    public void findMedian_successfully() {
        when(eventRepository.findMedian(any(OffsetDateTime.class), any(OffsetDateTime.class), anyString(),
                anyLong())).thenReturn(Optional.of(
                BigDecimal.ZERO));
        Optional<BigDecimal> result = eventService.findMedian(OffsetDateTime.now(), OffsetDateTime.MIN,
                TEMP_EVENT_TYPE, CLUSTER_ID);
        assertEquals(BigDecimal.ZERO, result.get());
        verify(eventRepository, times(1)).findMedian(any(), any(), anyString(), anyLong());
    }

}
