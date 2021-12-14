package com.sangam.iot.consumer.repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByTypeAndClusterId(String eventType, long clusterId);

    @Query("select avg (e.value) from Event e where e.timestamp >= :fromDate AND e.timestamp < :toDate AND"
            + " (:eventType is null or e.type = :eventType) AND "
            + "(:clusterId is null or e.clusterId = :clusterId)")
    Optional<BigDecimal> findAverage(OffsetDateTime fromDate, OffsetDateTime toDate, String eventType, Long clusterId);

    @Query("select max (e.value) from Event e where e.timestamp >= :fromDate AND e.timestamp < :toDate AND"
            + " (:eventType is null or e.type = :eventType) AND "
            + "(:clusterId is null or e.clusterId = :clusterId)")
    Optional<BigDecimal> findMax(OffsetDateTime fromDate, OffsetDateTime toDate, String eventType, Long clusterId);

    @Query("select min (e.value) from Event e where e.timestamp >= :fromDate AND e.timestamp < :toDate AND"
            + " (:eventType is null or e.type = :eventType) AND "
            + "(:clusterId is null or e.clusterId = :clusterId)")
    Optional<BigDecimal> findMin(OffsetDateTime fromDate, OffsetDateTime toDate, String eventType, Long clusterId);

    @Query(value = "select median(e.value) from Event e where e.creation_time >= :fromDate AND e.creation_time < :toDate AND"
            + " (:eventType is null or e.type = :eventType) AND "
            + "(:clusterId is null or e.cluster_id = :clusterId)", nativeQuery = true)
    Optional<BigDecimal> findMedian(OffsetDateTime fromDate, OffsetDateTime toDate, String eventType, Long clusterId);
}
