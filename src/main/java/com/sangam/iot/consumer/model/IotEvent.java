package com.sangam.iot.consumer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class IotEvent {
    private int id;
    private String type;
    private String name;
    private String clusterId;
    private OffsetDateTime timestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal value;
    private boolean initialized;

}
