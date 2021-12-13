CREATE TABLE EVENT
(
    id            BIGINT  auto_increment PRIMARY KEY,
    sensor_id      BIGINT NOT NULL,
    cluster_id     BIGINT NOT NULL,
    name          VARCHAR,
    type          VARCHAR NOT NULL,
    creation_time  TIMESTAMP WITH TIME ZONE,
    value         NUMERIC ,
    initialized   boolean
);

CREATE TABLE SENSOR
(
    id            BIGINT PRIMARY KEY,
    type          VARCHAR NOT NULL,
    clusterId     BIGINT NOT NULL,
    isActive      boolean,
    creation_date TIMESTAMP
);

