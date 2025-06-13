package dev.val.Hospital_Registration_API.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.val.Hospital_Registration_API.service.VisitService;
import dev.val.Hospital_Registration_API.dto.VisitDTO;
import dev.val.Hospital_Registration_API.model.Visit;
import dev.val.Hospital_Registration_API.model.Doctor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(VisitController.class)
public class VisitControllerTest {

    private Visit visit1;
    private Visit visit2;
    private Visit visit3;
    private Visit visit4;

    private Doctor doctor1;
    private Doctor doctor2;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VisitService visitService;

    @BeforeEach
    void setup() {
        doctor1 = new Doctor(1L, "Richard", "Henry");
        doctor2 = new Doctor(2L, "Jean", "Dupont");

        visit1 = new Visit(
                1L,
                "Valentin",
                "Herman",
                doctor1,
                LocalDateTime.of(2025, 6, 11, 16, 26, 47));
        visit2 = new Visit(
                2L,
                "Christophe",
                "Herman",
                doctor1,
                LocalDateTime.of(2025, 6, 12, 11, 9, 47));
        visit3 = new Visit(
                3L,
                "Ethan",
                "Loyens",
                doctor2,
                LocalDateTime.of(2025, 6, 12, 11, 9, 47));
        visit4 = new Visit(
                4L,
                "Pierre",
                "Barthélémy",
                null,
                LocalDateTime.of(2025, 2, 24, 11, 9, 47));
    }

    @Test
    void allVisitsTest() throws Exception {
        List<VisitDTO> visits = Stream.of(
            new VisitDTO(1L, "Valentin Herman", "Doctor A", LocalDate.of(2025, 06, 12)),
            new VisitDTO(2L, "Christophe Herman", "Doctor B", LocalDate.of(2025, 06, 11))
        ).toList();
        when(visitService.getAllVisits()).thenReturn(visits);

        mockMvc.perform(get("/hospital-registration-api/v1/visits"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].visitorName").value("Valentin Herman"))
               .andExpect(jsonPath("$[1].visitorName").value("Christophe Herman"));
    }

    @Test
    void foundOneVisitByDateTest() throws Exception {
        List<Visit> visits = Stream.of(visit1).toList();
        when(visitService.getAllVisitsByDate(LocalDate.of(2025, 06, 11))).thenReturn(visits);

        mockMvc.perform(get("/hospital-registration-api/v1/visits/by-date")
                        .param("date", "2025-06-11"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1))
               .andExpect(jsonPath("$[0].firstName").value("Valentin"));
    }

    @Test
    void foundMultipleVisitsByDateTest() throws Exception {
        List<Visit> visits = Stream.of(visit2, visit3).toList();
        when(visitService.getAllVisitsByDate(LocalDate.of(2025, 06, 12))).thenReturn(visits);

        mockMvc.perform(get("/hospital-registration-api/v1/visits/by-date")
                        .param("date", "2025-06-12"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void foundNoVisitByDateTest() throws Exception {
        when(visitService.getAllVisitsByDate(LocalDate.of(2025, 03, 22))).thenReturn(List.of());

        mockMvc.perform(get("/hospital-registration-api/v1/visits/by-date")
                        .param("date", "2025-03-22"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }
}