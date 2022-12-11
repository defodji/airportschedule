package com.boeing.airportschedule.controller;

import com.boeing.airportschedule.model.dto.ArrivalDto;
import com.boeing.airportschedule.model.dto.DepartureDto;
import com.boeing.airportschedule.model.entity.Arrival;
import com.boeing.airportschedule.model.entity.Departure;
import com.boeing.airportschedule.service.ArrivalService;
import com.boeing.airportschedule.service.DepartureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AirportScheduleController {
    private final DepartureService departureService;
    private final ArrivalService arrivalService;

    @GetMapping("/departures")
    @CrossOrigin(origins = "*")
    public List<Departure> getDepartures() {
        return departureService.getDepartures();
    }

    @GetMapping("arrivals")
    @CrossOrigin(origins = "*")
    public List<Arrival> getArrivals() {
        return arrivalService.getArrivals();
    }

    @PostMapping(value = "/departures", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Departure> addDeparture(@RequestBody DepartureDto departure) {
        Departure departureResult = departureService.createDeparture(departure);
        return new ResponseEntity<>(departureResult, HttpStatus.CREATED);
    }

    @PostMapping(value = "/arrivals", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Arrival> addArrival(@RequestBody ArrivalDto arrival) {
        Arrival arrivalResult = arrivalService.createArrival(arrival);
        return new ResponseEntity<>(arrivalResult, HttpStatus.CREATED);
    }
}
