package com.boeing.airportschedule.service;

import com.boeing.airportschedule.AirportscheduleApplication;
import com.boeing.airportschedule.model.dto.DepartureDto;
import com.boeing.airportschedule.model.entity.Departure;
import com.boeing.airportschedule.repository.DepartureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.List;

import static com.boeing.airportschedule.service.ArrivalService.LIMIT_MINUTES_AFTER_LANDED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AirportscheduleApplication.class)
@ActiveProfiles("test")
class DepartureServiceTest {
    @Autowired
    private DepartureService departureService;

    @Autowired
    private DepartureRepository departureRepository;

    @Test
    @Sql(scripts = {"/db/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getDepartures_ThenEmptyList() {
        List<Departure> departures = departureService.getDepartures();
        assertTrue(departures.isEmpty());
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql", "/db/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getDepartures_ThenListWithDepartures() {
        LocalTime now = LocalTime.now();
        List<Departure> departures = departureService.getDepartures();
        assertTrue(departures.size() > 0);
        departures.forEach( departure -> {
            assertTrue(departure.getActualTime().isAfter(now.minusMinutes(LIMIT_MINUTES_AFTER_LANDED)) );
            assertNotNull(departure.getStatus());
        });
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createDeparture_thenGetNew() {
        // before creating
        List<Departure> departuresBefore = departureRepository.findAll();
        assertTrue(departuresBefore.size() == 0);

        DepartureDto departureDto = new DepartureDto();
        departureDto.setFlightNumber("TT4546");
        departureDto.setAirline("Toto Airlines");
        departureDto.setTo("Darfur, Sudan");
        departureDto.setAircraft("B522");
        departureDto.setScheduledTime(LocalTime.now());
        Departure departure = departureService.createDeparture(departureDto);

        List<Departure> departures = departureRepository.findAll();
        assertTrue(departures.size() == 1);

        assertEquals(departureDto.getFlightNumber(), departure.getFlightNumber());
        assertEquals(departureDto.getTo(), departure.getTo());
        assertEquals(departureDto.getAirline(), departure.getAirline());
        assertEquals(departureDto.getAircraft(), departure.getAircraft());
        assertEquals(departureDto.getScheduledTime(), departure.getScheduledTime());
        assertNotNull(departure.getActualTime());
    }
}