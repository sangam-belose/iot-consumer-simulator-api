package com.sangam.iot.consumer.processor;

import com.sangam.iot.consumer.config.IntegrationTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("Integ")
@Import(IntegrationTestConfig.class)
public class ProcessorIT {

    @Test
    void readIotEvent() {

    }
}
