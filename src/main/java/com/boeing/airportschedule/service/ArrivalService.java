package com.boeing.airportschedule.service;

import com.boeing.airportschedule.model.dto.ArrivalDto;
import com.boeing.airportschedule.model.entity.Arrival;
import com.boeing.airportschedule.repository.ArrivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import static com.boeing.airportschedule.util.Utils.getRandomDelay;

@Service
@RequiredArgsConstructor
public class ArrivalService {
    public static final int LIMIT_MINUTES_AFTER_LANDED = 15;
    public static final int MINIMUM_MINUTES_FOR_DELAY = 15;
    private final ArrivalRepository repository;

    public List<Arrival> getArrivals() {
        List<Arrival> arrivals = repository.findByActualTimeGreaterThanOrderByScheduledTimeAsc(
                LocalTime.now().minusMinutes(LIMIT_MINUTES_AFTER_LANDED));
        arrivals.forEach( arrival -> arrival.setStatus(getStatus(arrival)));
        return arrivals;
    }

    private String getStatus(Arrival arrival) {
        if (arrival.getActualTime().isAfter(arrival.getScheduledTime().plusMinutes(MINIMUM_MINUTES_FOR_DELAY))) {
            return "Delayed";
        }
        if (arrival.getActualTime().isBefore(LocalTime.now())) {
            return "Landed";
        }
        return "Estimated";
    }

    public Arrival createArrival(ArrivalDto arrivalInput) {
        Arrival arrival = Arrival.builder()
                .scheduledTime(arrivalInput.getScheduledTime())
                .from(arrivalInput.getFrom())
                .flightNumber(arrivalInput.getFlightNumber())
                .airline(arrivalInput.getAirline())
                .aircraft(arrivalInput.getAircraft())
                .actualTime(arrivalInput.getScheduledTime().plusMinutes(getRandomDelay()))
                .build();
        return repository.save(arrival);
    }
}
