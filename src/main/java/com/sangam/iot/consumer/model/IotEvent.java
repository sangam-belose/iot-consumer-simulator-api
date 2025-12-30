package com.sangam.iot.consumer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sangam.iot.consumer.util.OffsetDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IotEvent {
    private int id;
    private String type;
    private String name;
    private String clusterId;
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    private OffsetDateTime timestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal value;
    private boolean initialized;

}
