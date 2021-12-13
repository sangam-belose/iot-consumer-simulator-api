package com.sangam.iot.consumer.service;

import static org.junit.Assert.assertEquals;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {

    private static final String TEMP_EVENT_TYPE = "TEMPERATURE";
    private static final long CLUSTER_ID = 1l;

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Test
    public void findAverage_successfully() {
        when(eventService.findAverage(any(OffsetDateTime.class), any(OffsetDateTime.class),
                anyString(), anyLong())).thenReturn(Optional.of(
                BigDecimal.TEN));
        Optional<BigDecimal> result = eventService.findAverage(OffsetDateTime.now(),
                OffsetDateTime.MIN, TEMP_EVENT_TYPE, CLUSTER_ID);
        assertEquals(BigDecimal.TEN, result.get());
        verify(eventRepository, times(1)).findAverage(any(), any(), anyString(), anyLong());
    }

    public void findMax_successfully() {
        when(eventService.findMax(any(OffsetDateTime.class), any(OffsetDateTime.class), anyString(),
                anyLong())).thenReturn(Optional.of(
                BigDecimal.ONE));
        Optional<BigDecimal> result = eventService.findMax(OffsetDateTime.now(), OffsetDateTime.MAX,
                TEMP_EVENT_TYPE, CLUSTER_ID);
        assertEquals(BigDecimal.ONE, result.get());
        verify(eventRepository, times(1)).findMax(any(), any(), anyString(), anyLong());
    }

    public void findMin_successfully() {
        when(eventService.findMin(any(OffsetDateTime.class), any(OffsetDateTime.class), anyString(),
                anyLong())).thenReturn(Optional.of(
                BigDecimal.ZERO));
        Optional<BigDecimal> result = eventService.findMin(OffsetDateTime.now(), OffsetDateTime.MIN,
                TEMP_EVENT_TYPE, CLUSTER_ID);
        assertEquals(BigDecimal.ZERO, result.get());
        verify(eventRepository, times(1)).findMin(any(), any(), anyString(), anyLong());
    }

    public void findMedian_successfully() {
        when(eventService.findMedian(any(OffsetDateTime.class), any(OffsetDateTime.class),
                anyString(), anyLong())).thenReturn(Optional.of(
                BigDecimal.ZERO));
        Optional<BigDecimal> result = eventService.findMedian(OffsetDateTime.now(),
                OffsetDateTime.MIN, TEMP_EVENT_TYPE, CLUSTER_ID);
        assertEquals(BigDecimal.ZERO, result.get());
        verify(eventRepository, times(1)).findMedian(any(), any(), anyString(), anyLong());
    }

}
