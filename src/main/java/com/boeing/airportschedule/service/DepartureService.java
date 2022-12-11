package com.boeing.airportschedule.service;

import com.boeing.airportschedule.model.dto.DepartureDto;
import com.boeing.airportschedule.model.entity.Departure;
import com.boeing.airportschedule.repository.DepartureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import static com.boeing.airportschedule.util.Utils.getRandomDelay;

@Service
@RequiredArgsConstructor
public class DepartureService {
    public static final int LIMIT_MINUTES_AFTER_DEPARTED = 15;
    public static final int MINIMUM_MINUTES_FOR_DELAY = 15;
    private final DepartureRepository repository;

    public List<Departure> getDepartures() {
        List<Departure> departures = repository.findByActualTimeGreaterThanOrderByScheduledTimeAsc(
                LocalTime.now().minusMinutes(LIMIT_MINUTES_AFTER_DEPARTED));
        departures.forEach( departure -> departure.setStatus(getStatus(departure)));
        return departures;
    }

    private String getStatus(Departure departure) {
        if (departure.getActualTime().isAfter(departure.getScheduledTime().plusMinutes(MINIMUM_MINUTES_FOR_DELAY))) {
            return "Delayed";
        }
        if (departure.getActualTime().isBefore(LocalTime.now())) {
            return "Departed";
        }
        return "Estimated dep.";
    }

    public Departure createDeparture(DepartureDto departureInput) {
        Departure departure = Departure.builder()
                .flightNumber(departureInput.getFlightNumber())
                .scheduledTime(departureInput.getScheduledTime())
                .to(departureInput.getTo())
                .airline(departureInput.getAirline())
                .aircraft(departureInput.getAircraft())
                .actualTime(departureInput.getScheduledTime().plusMinutes(getRandomDelay()))
                .build();
        return repository.save(departure);
    }
}
