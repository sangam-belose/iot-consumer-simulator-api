package com.sangam.iot.consumer.repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
