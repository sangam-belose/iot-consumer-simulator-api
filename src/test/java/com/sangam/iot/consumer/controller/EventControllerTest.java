package com.sangam.iot.consumer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sangam.iot.consumer.service.EventService;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EventsController.class)
public class EventControllerTest {

    private static final String AVG_EVENT_URL = "/events/average";
    private static final String MAX_EVENT_URL = "/events/max";
    private static final String MIN_EVENT_URL = "/events/min";
    private static final String MEDIAN_EVENT_URL = "/events/median";

    private static final String TEMP_EVENT_TYPE = "TEMPERATURE";
    private static final long CLUSTER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Test
    public void getAverageEvents() throws Exception {

        BigDecimal avg = BigDecimal.TEN;

        OffsetDateTime toDate = OffsetDateTime.now();
        OffsetDateTime fromDate = toDate.minusMinutes(2);

        when(eventService.findAverage(fromDate, toDate, TEMP_EVENT_TYPE, CLUSTER_ID))
                .thenReturn(
                        Optional.of(avg));

        mockMvc.perform(get(AVG_EVENT_URL)
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString())
                        .param("type", TEMP_EVENT_TYPE)
                        .param("clusterId", String.valueOf(CLUSTER_ID))
                )
                .andExpect(status().isOk());

    }

    @Test
    public void getMaxEvents() throws Exception {

        BigDecimal max = BigDecimal.TEN;

        OffsetDateTime toDate = OffsetDateTime.now();
        OffsetDateTime fromDate = toDate.minusMinutes(2);

        when(eventService.findMax(fromDate, toDate, TEMP_EVENT_TYPE, CLUSTER_ID))
                .thenReturn(
                        Optional.of(max));

        mockMvc.perform(get(MAX_EVENT_URL)
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString())
                        .param("type", TEMP_EVENT_TYPE)
                        .param("clusterId", String.valueOf(CLUSTER_ID))
                )
                .andExpect(status().isOk());

    }

    @Test
    public void getMinEvents() throws Exception {

        BigDecimal min = BigDecimal.ONE;

        OffsetDateTime toDate = OffsetDateTime.now();
        OffsetDateTime fromDate = toDate.minusMinutes(2);

        when(eventService.findMin(fromDate, toDate, TEMP_EVENT_TYPE, CLUSTER_ID))
                .thenReturn(
                        Optional.of(min));

        mockMvc.perform(get(MIN_EVENT_URL)
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString())
                        .param("type", TEMP_EVENT_TYPE)
                        .param("clusterId", String.valueOf(CLUSTER_ID))
                )
                .andExpect(status().isOk());

    }

    @Test
    public void getMedianEvents() throws Exception {

        BigDecimal median = BigDecimal.TEN;

        OffsetDateTime toDate = OffsetDateTime.now();
        OffsetDateTime fromDate = toDate.minusMinutes(2);

        when(eventService.findMedian(fromDate, toDate, TEMP_EVENT_TYPE, CLUSTER_ID))
                .thenReturn(
                        Optional.of(median));

        mockMvc.perform(get(MEDIAN_EVENT_URL)
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString())
                        .param("type", TEMP_EVENT_TYPE)
                        .param("clusterId", String.valueOf(CLUSTER_ID))
                )
                .andExpect(status().isOk());

    }

}
