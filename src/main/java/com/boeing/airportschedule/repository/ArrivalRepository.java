package com.boeing.airportschedule.repository;

import com.boeing.airportschedule.model.entity.Arrival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ArrivalRepository extends JpaRepository<Arrival, String> {
    List<Arrival> findByActualTimeGreaterThanOrderByScheduledTimeAsc(LocalTime limit);
}
