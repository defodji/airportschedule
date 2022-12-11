package com.boeing.airportschedule.repository;

import com.boeing.airportschedule.model.entity.Departure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface DepartureRepository extends JpaRepository<Departure, String> {
    List<Departure> findByActualTimeGreaterThanOrderByScheduledTimeAsc(LocalTime limit);
}
