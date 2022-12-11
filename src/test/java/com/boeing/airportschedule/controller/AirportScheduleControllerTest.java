package com.boeing.airportschedule.controller;

import com.boeing.airportschedule.model.dto.ArrivalDto;
import com.boeing.airportschedule.model.dto.DepartureDto;
import com.boeing.airportschedule.model.entity.Arrival;
import com.boeing.airportschedule.model.entity.Departure;
import com.boeing.airportschedule.service.ArrivalService;
import com.boeing.airportschedule.service.DepartureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AirportScheduleController.class)
class AirportScheduleControllerTest {
    @MockBean
    private DepartureService departureService;

    @MockBean
    private ArrivalService arrivalService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getDepartures() throws Exception {
        LocalTime now = LocalTime.now();
        Departure departure = Departure.builder()
                .flightNumber("QA2345")
                .to("Chicago, USA")
                .airline("Qantas")
                .aircraft("B720")
                .status("Delayed")
                .scheduledTime(now).actualTime(now).build();
        List<Departure> departures = List.of(departure);

        when(departureService.getDepartures()).thenReturn(departures);

        mockMvc.perform(get("/departures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].flightNumber", is("QA2345")))
                .andExpect(jsonPath("$[0].to", is("Chicago, USA")))
                .andExpect(jsonPath("$[0].airline", is("Qantas")))
                .andExpect(jsonPath("$[0].aircraft", is("B720")))
                .andExpect(jsonPath("$[0].status", is("Delayed")))
                .andExpect(jsonPath("$[0].scheduledTime", startsWith(now.toString().substring(0, 8))))
                .andExpect(jsonPath("$[0].actualTime", startsWith(now.toString().substring(0, 8))));
    }

    @Test
    void getArrivals() throws Exception {
        LocalTime now = LocalTime.now();
        Arrival arrival = Arrival.builder()
                .flightNumber("JP2345")
                .from("Tokyo, Japan")
                .airline("Japan Airlines")
                .aircraft("B720")
                .status("Landed")
                .scheduledTime(now).actualTime(now).build();
        List<Arrival> arrivals = List.of(arrival);

        when(arrivalService.getArrivals()).thenReturn(arrivals);

        mockMvc.perform(get("/arrivals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].flightNumber", is("JP2345")))
                .andExpect(jsonPath("$[0].from", is("Tokyo, Japan")))
                .andExpect(jsonPath("$[0].airline", is("Japan Airlines")))
                .andExpect(jsonPath("$[0].aircraft", is("B720")))
                .andExpect(jsonPath("$[0].status", is("Landed")))
                .andExpect(jsonPath("$[0].scheduledTime", startsWith(now.toString().substring(0, 8))))
                .andExpect(jsonPath("$[0].actualTime", startsWith(now.toString().substring(0, 8))));
    }

    @Test
    void addDeparture() throws Exception {
        LocalTime now = LocalTime.now();
        Departure departure = Departure.builder()
                .flightNumber("QA2345")
                .to("Chicago, USA")
                .airline("Qantas")
                .aircraft("B720")
                .status("Delayed")
                .scheduledTime(now).actualTime(now).build();

        DepartureDto departureDto = DepartureDto.builder()
                .flightNumber("QA2345")
                .to("Chicago, USA")
                .airline("Qantas")
                .aircraft("B720")
                .scheduledTime(now).build();

        String departureJson = "{\n" +
                "\"flightNumber\":\"QA2345\",\n" +
                "\"scheduledTime\":\"" + now.toString() + "\",\n" +
                "\"to\":\"Chicago, USA\",\n" +
                "\"airline\": \"Qantas\",\n" +
                "\"aircraft\": \"B720\"\n" +
                "}";
        when(departureService.createDeparture(departureDto)).thenReturn(departure);
        mockMvc.perform(post("/departures").accept(MediaType.APPLICATION_JSON)
                .content(departureJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flightNumber", is("QA2345")))
                .andExpect(jsonPath("$.to", is("Chicago, USA")))
                .andExpect(jsonPath("$.airline", is("Qantas")))
                .andExpect(jsonPath("$.aircraft", is("B720")))
                .andExpect(jsonPath("$.status", is("Delayed")))
                .andExpect(jsonPath("$.scheduledTime", startsWith(now.toString())))
                .andExpect(jsonPath("$.actualTime", startsWith(now.toString())));
    }

    @Test
    void addArrival() throws Exception {
        LocalTime now = LocalTime.now();
        Arrival arrival = Arrival.builder()
                .flightNumber("JP2345")
                .from("Tokyo, Japan")
                .airline("Japan Airlines")
                .aircraft("B720")
                .status("Landed")
                .scheduledTime(now).actualTime(now).build();

        ArrivalDto arrivalDto = ArrivalDto.builder()
                .flightNumber("JP2345")
                .from("Tokyo, Japan")
                .airline("Japan Airlines")
                .aircraft("B720")
                .scheduledTime(now).build();

        String arrivalJson = "{\n" +
                "\"flightNumber\":\"JP2345\",\n" +
                "\"scheduledTime\":\"" + now.toString() + "\",\n" +
                "\"from\":\"Tokyo, Japan\",\n" +
                "\"airline\": \"Japan Airlines\",\n" +
                "\"aircraft\": \"B720\"\n" +
                "}";

        when(arrivalService.createArrival(arrivalDto)).thenReturn(arrival);
        mockMvc.perform(post("/arrivals").accept(MediaType.APPLICATION_JSON)
                        .content(arrivalJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flightNumber", is("JP2345")))
                .andExpect(jsonPath("$.from", is("Tokyo, Japan")))
                .andExpect(jsonPath("$.airline", is("Japan Airlines")))
                .andExpect(jsonPath("$.aircraft", is("B720")))
                .andExpect(jsonPath("$.status", is("Landed")))
                .andExpect(jsonPath("$.scheduledTime", startsWith(now.toString().substring(0,8))))
                .andExpect(jsonPath("$.actualTime", startsWith(now.toString().substring(0, 8))));
    }
}