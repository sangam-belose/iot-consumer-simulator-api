package com.sangam.iot.consumer.processor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangam.iot.consumer.model.EventAggregator;
import com.sangam.iot.consumer.model.EventsWrapper;
import com.sangam.iot.consumer.model.IotEvent;
import com.sangam.iot.consumer.service.EventService;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Suppressed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.WindowStore;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Processor {

    @Autowired
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public void process(StreamsBuilder streamsBuilder) {
        KStream<Long,String> eventInput
                = streamsBuilder.stream("iot-data",
                Consumed.with(
                        Serdes.Long(), Serdes.String()));

        //ObjectMapper mapper = new ObjectMapper();

        //Convert input json to IotEvent object using Object Mapper
        KStream<Long, IotEvent> orderObjects
                = eventInput.mapValues(
                new ValueMapper<String, IotEvent>() {
                    @Override
                    public IotEvent apply(String inputJson) {

                        try {
                            String payload = inputJson.split("\\.")[0];
                            String decodedMessage = new String(Base64.decodeBase64(payload));
                            IotEvent order =
                                    objectMapper.readValue(
                                            decodedMessage,
                                            IotEvent.class);

                            eventService.saveEvent(order);
                            return order;
                        }
                        catch(Exception e) {
                           log.error("ERROR : Cannot convert JSON "
                                    + inputJson);
                           e.printStackTrace();
                            return null;
                        }
                    }
                }
        );

        //Print objects received
        orderObjects.peek(
                (key, value) ->
                        log.info("Received Event : " + value)
        );


        //Create a window of 5 seconds
        TimeWindows tumblingWindow = TimeWindows
                .of(Duration.ofSeconds(5))
                .grace(Duration.ZERO);

        //Initializer creates a new aggregator for every
        //Window & Product combination
        Initializer<EventAggregator> orderAggregatorInitializer
                = EventAggregator::new;

        //Aggregator - Compute total value and call the aggregator
        Aggregator<String, IotEvent, EventAggregator> orderAdder
                = (key, value, aggregate)
                -> aggregate.add(Double.valueOf(value.getClusterId())
                * value.getId());

        //Perform Aggregation
        /*KTable<Windowed<String>,EventAggregator> productSummary
                = orderObjects
                .groupBy( //Group by Product
                        (key,value) -> value.getProduct() ,
                        Grouped.with(Serdes.Long(),orderSerde))
                .windowedBy(tumblingWindow)
                .aggregate(
                        orderAggregatorInitializer,
                        orderAdder,
                        //Store output in a materialized store
                        Materialized.<String, EventAggregator,
                                        WindowStore<Bytes, byte[]>>as(
                                        "time-windowed-aggregate-store")
                                .withValueSerde(aggregatorSerde))
                .suppress(
                        Suppressed
                                .untilWindowCloses(
                                        Suppressed.BufferConfig
                                                .unbounded()
                                                .shutDownWhenFull()));

        productSummary
                .toStream() //convert KTable to KStream
                .foreach( (key, aggregation) ->
                        {
                            System.out.println("Received Summary :" +
                                    " Window = " + key.window().startTime() +
                                    " Product =" + key.key() +
                                    " Value = " + aggregation.getTotalValue());

                            //Write to order_summary table
                            dbUpdater.insertSummary(
                                    key.window().startTime().toString(),
                                    key.key(),
                                    aggregation.getTotalValue()
                            );
                        }
                );*/

    }

}
