package com.boeing.airportschedule.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalDto {
    private String flightNumber;

    private LocalTime scheduledTime;

    private String from;

    private String airline;

    private String aircraft;
}
