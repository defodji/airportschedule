package com.boeing.airportschedule.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalTime;

@Entity
@Table(name="arrival")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Arrival {
    @Id
    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "scheduled_time", nullable = false)
    private LocalTime scheduledTime;

    @Column(name = "actual_time", nullable = false)
    private LocalTime actualTime;

    @Column(name = "city_from", nullable = false)
    private String from;

    @Column(name = "airline", nullable = false)
    private String airline;

    @Column(name = "aircraft")
    private String aircraft;

    @Transient
    private String status;
}
