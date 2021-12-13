package com.sangam.iot.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class IotConsumerSimulatorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotConsumerSimulatorApiApplication.class, args);
    }

}
