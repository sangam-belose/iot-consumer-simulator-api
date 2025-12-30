package com.sangam.iot.consumer.controller;

import com.sangam.iot.consumer.config.IntegrationTestConfig;
import com.sangam.iot.consumer.model.IotEvent;
import com.sangam.iot.consumer.repository.Event;
import com.sangam.iot.consumer.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(IntegrationTestConfig.class)
public class EventControllerIT {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @Autowired
    private EventRepository eventRepository;

    Clock clock = Clock.fixed(Instant.ofEpochSecond(1767129703), ZoneId.of("UTC"));

    private Long savedId;

    @BeforeEach
    void setup() {
        // build client against running server
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        //clear before saving new record
        eventRepository.deleteAll();

        Event tempEvent = new Event();
        tempEvent.setClusterId(10L);
        tempEvent.setSensorId(10L);
        tempEvent.setType("TEMPERATURE");
        tempEvent.setName("temperature");
        tempEvent.setValue(BigDecimal.valueOf(10.25));
        tempEvent.setTimestamp(OffsetDateTime.now(clock));
        eventRepository.saveAndFlush(tempEvent);

        this.savedId = tempEvent.getId();
    }

    @Test
    void testGetAllEventsSuccessfully() {
        var expectedList = List.of(
                new IotEvent(
                        savedId.intValue(), "TEMPERATURE", "temperature", "10", OffsetDateTime.now(clock), BigDecimal.valueOf(10.25), true)
                );

       var iotEventList = webTestClient.get()
                .uri("/events/all")
                .exchange()
                .expectStatus().isOk()
               .expectBodyList(IotEvent.class)
               .returnResult()
                       .getResponseBody();

        assertThat(iotEventList)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedList);

    }
}
