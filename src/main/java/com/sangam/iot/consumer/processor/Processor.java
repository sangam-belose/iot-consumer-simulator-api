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
                        IotEvent order =
                                objectMapper.readValue(
                                        decodedMessage,
                                        IotEvent.class);

                        eventService.saveEvent(order);
                        return order;
                    } catch (Exception e) {
                        log.error("ERROR : Cannot convert JSON {}", inputJson);
                        e.printStackTrace();
                        return null;
                    }
                }
        );

        //Print objects received
        orderObjects.peek(
                (key, value) ->
                        log.info("Received Event : {}", value)
        );

    }

}
