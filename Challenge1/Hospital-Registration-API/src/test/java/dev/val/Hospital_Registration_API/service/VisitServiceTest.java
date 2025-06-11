package dev.val.Hospital_Registration_API.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.val.Hospital_Registration_API.dto.VisitDTO;
import dev.val.Hospital_Registration_API.mapper.VisitDTOMapper;
import dev.val.Hospital_Registration_API.model.Doctor;
import dev.val.Hospital_Registration_API.model.Visit;
import dev.val.Hospital_Registration_API.repository.VisitRepository;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.stream.Stream;
import java.util.List;

public class VisitServiceTest {

    private VisitService visitService;
    private VisitRepository visitRepository;
    private VisitDTOMapper visitDTOMapper;

    private Doctor doctor1;
    private Doctor doctor2;

    private Visit visit1;
    private Visit visit2;
    private Visit visit3;
    private Visit visit4;

    @BeforeEach
    void setup() {
        visitRepository = mock(VisitRepository.class);
        visitDTOMapper = mock(VisitDTOMapper.class);
        visitService = new VisitService(visitRepository, visitDTOMapper);

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
    void addVisitTest() {
        visitService.addVisit(visit1);
        verify(visitRepository).save(visit1);
    }

    @Test
    void getAllVisitsTest() {
        when(visitRepository.findAll()).thenReturn(Stream.of(visit1, visit2).toList());
        when(visitDTOMapper.apply(visit1)).thenReturn(new VisitDTO(
                1L,
                "Valentin Herman",
                "Richard Henry",
                LocalDate.of(2025, 6, 11)));
        when(visitDTOMapper.apply(visit2)).thenReturn(new VisitDTO(
                2L,
                "Christophe Herman",
                "Richard Henry",
                LocalDate.of(2025, 6, 12)));
        List<VisitDTO> result = visitService.getAllVisits();
        assertEquals(2, result.size());
        assertEquals("Valentin Herman", result.get(0).visitorName());
        assertEquals("Richard Henry", result.get(1).doctorName());
    }

    @Test
    void foundOneVisitByDateTest() {
        when(visitRepository.findAll()).thenReturn(Stream.of(visit1, visit2).toList());
        List<Visit> result = visitService.getAllVisitsByDate(LocalDate.of(2025, 6, 11));
        assertEquals(1, result.size());
        assertEquals("Valentin", result.get(0).getFirstName());
    }

    @Test
    void foundMultipleVisitsByDateTest() {
        when(visitRepository.findAll()).thenReturn(Stream.of(visit1, visit2, visit3).toList());
        List<Visit> result = visitService.getAllVisitsByDate(LocalDate.of(2025, 6, 12));
        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getId());
        assertEquals(3L, result.get(1).getId());
    }

    @Test
    void foundNoVisitByDateTest() {
        when(visitRepository.findAll()).thenReturn(Stream.of(visit1, visit2, visit3).toList());
        List<Visit> result = visitService.getAllVisitsByDate(LocalDate.of(2024, 6, 07));
        assertEquals(0, result.size());
    }

    @Test
    void foundOneVisitByPeriodTest(){
       when(visitRepository.findAll()).thenReturn(Stream.of(visit1, visit2, visit3, visit4).toList()); 
       List<Visit> result = visitService.getAllVisitsByPeriod(LocalDate.of(2025, 04, 11), LocalDate.of(2025, 6, 11));
       assertEquals(1, result.size());
       assertEquals(1L, result.get(0).getId());
    }

    @Test
    void foundMultipleVisitsByPeriodTest(){
        when(visitRepository.findAll()).thenReturn(Stream.of(visit1, visit2, visit3, visit4).toList());
        List<Visit> result = visitService.getAllVisitsByPeriod(LocalDate.of(2025, 04, 11), LocalDate.of(2025, 07, 24));
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(3L, result.get(2).getId());
    }

    @Test
    void foundNoVisitByPeriodTest(){
        when(visitRepository.findAll()).thenReturn(Stream.of(visit1, visit2, visit3, visit4).toList());
        List<Visit> result = visitService.getAllVisitsByPeriod(LocalDate.of(2025, 07, 11), LocalDate.of(2025, 8, 24));
        assertEquals(0, result.size()); 
    }
}