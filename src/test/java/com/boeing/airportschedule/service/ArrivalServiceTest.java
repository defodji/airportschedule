package com.boeing.airportschedule.service;

import com.boeing.airportschedule.AirportscheduleApplication;
import com.boeing.airportschedule.model.dto.ArrivalDto;
import com.boeing.airportschedule.model.entity.Arrival;
import com.boeing.airportschedule.repository.ArrivalRepository;
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
class ArrivalServiceTest {
    @Autowired
    private ArrivalService arrivalService;

    @Autowired
    private ArrivalRepository arrivalRepository;

    @Test
    @Sql(scripts = {"/db/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getArrivals_WhenEmpty() {
        List<Arrival> arrivals = arrivalService.getArrivals();
        assertTrue(arrivals.isEmpty());
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql", "/db/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getArrivals_whenArrivals() {
        LocalTime now = LocalTime.now();
        List<Arrival> arrivals = arrivalService.getArrivals();
        assertTrue(arrivals.size() > 0);
        arrivals.forEach( arrival -> {
            assertTrue(arrival.getActualTime().isAfter(now.minusMinutes(LIMIT_MINUTES_AFTER_LANDED)) );
            assertNotNull(arrival.getStatus());
        });
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void create_thenGetNew() {
        // before creating
        List<Arrival> arrivalsBefore = arrivalRepository.findAll();
        assertTrue(arrivalsBefore.size() == 0);

        ArrivalDto arrivalDto = new ArrivalDto();
        arrivalDto.setFlightNumber("TT4546");
        arrivalDto.setAirline("Toto Airlines");
        arrivalDto.setFrom("Darfur, Sudan");
        arrivalDto.setAircraft("B522");
        arrivalDto.setScheduledTime(LocalTime.now());
        Arrival arrival = arrivalService.createArrival(arrivalDto);
        System.out.println("Actual time: " + arrival.getActualTime());

        List<Arrival> arrivals = arrivalRepository.findAll();
        assertTrue(arrivals.size() == 1);

        assertEquals(arrivalDto.getFlightNumber(), arrival.getFlightNumber());
        assertEquals(arrivalDto.getFrom(), arrival.getFrom());
        assertEquals(arrivalDto.getAirline(), arrival.getAirline());
        assertEquals(arrivalDto.getAircraft(), arrival.getAircraft());
        assertEquals(arrivalDto.getScheduledTime(), arrival.getScheduledTime());
        assertNotNull(arrival.getActualTime());
    }
}