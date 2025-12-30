package com.sangam.iot.consumer.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangam.iot.consumer.model.IotEvent;
import com.sangam.iot.consumer.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static java.util.Base64.getDecoder;

@Component
@Slf4j
public class Processor {

    @Autowired
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public void process(StreamsBuilder streamsBuilder) {
        KStream<Long, String> eventInput
                = streamsBuilder.stream("iot-data",
                Consumed.with(
                        Serdes.Long(), Serdes.String()));

        //Convert input json to IotEvent object using Object Mapper
        KStream<Long, IotEvent> orderObjects
                = eventInput.mapValues(
                inputJson -> {

                    try {
                        String payload = inputJson.split("\\.")[0];
                        String decodedMessage = new String(getDecoder().decode(payload));
                        // do not save here to avoid duplicate writes
                        return objectMapper.readValue(
                                decodedMessage,
                                IotEvent.class);
                    } catch (Exception e) {
                        log.error("ERROR : Cannot convert JSON {}", inputJson, e);
                        return null;
                    }
                }
        );

        // Save events and compute real-time metrics (last 1 minute)
        orderObjects.peek(
                (key, value) -> {
                    if(value == null) {
                        return;
                    }
                    try {
                        // persist event
                        eventService.saveEvent(value);

                        // compute a simple sliding window of 1 minute (adjust as needed)
                        OffsetDateTime to = OffsetDateTime.now();
                        OffsetDateTime from = to.minusMinutes(1);

                        Long clusterId = Long.parseLong(value.getClusterId());
                        String eventType = value.getType();

                        Optional<BigDecimal> avg = eventService.findAverage(from, to, eventType, clusterId);
                        Optional<BigDecimal> max = eventService.findMax(from, to, eventType, clusterId);
                        Optional<BigDecimal> min = eventService.findMin(from, to, eventType, clusterId);
                        Optional<BigDecimal> median = eventService.findMedian(from, to, eventType, clusterId);

                        log.info("Realtime metrics for cluster={} type={} window=last1m -> avg={} max={} min={} median={} ",
                                clusterId,
                                eventType,
                                avg.map(BigDecimal::toPlainString).orElse("N/A"),
                                max.map(BigDecimal::toPlainString).orElse("N/A"),
                                min.map(BigDecimal::toPlainString).orElse("N/A"),
                                median.map(BigDecimal::toPlainString).orElse("N/A")
                        );
                    } catch (Exception e) {
                        log.error("Error Computing realtime metrics for event {}", value, e);
                    }
                }

        );

    }

}
