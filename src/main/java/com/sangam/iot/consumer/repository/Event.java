package com.sangam.iot.consumer.repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "EVENT")
@Data
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "sensorId")
    private long sensorId;
    @Column(name = "clusterId")
    private long clusterId;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "creationTime", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime timestamp;
    @Column(name = "value")
    private BigDecimal value;
    @Column(name = "initialized")
    private boolean initialized;


}
