package com.sangam.iot.consumer.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class IntegrationTestConfig {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> getPostgres() {
        return new PostgreSQLContainer<>("postgres:18.1-alpine");
    }

    @Bean
    @ServiceConnection
    public ConfluentKafkaContainer getKafka() {
        DockerImageName myImage = DockerImageName.parse("confluentinc/cp-kafka:7.5.1").asCompatibleSubstituteFor("apache/kafka");
        return new ConfluentKafkaContainer(myImage);
    }
}
