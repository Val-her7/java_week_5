package dev.val.Hospital_Registration_API.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dev.val.Hospital_Registration_API.repository.DoctorRepository;
import dev.val.Hospital_Registration_API.model.Doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.Optional;
import java.util.stream.Stream;

public class DoctorServiceTest {

    private DoctorService doctorService;
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setup() {
        doctorRepository = mock(DoctorRepository.class);
        doctorService = new DoctorService(doctorRepository);
    }

    @Test
    void addDoctorTest() {
        Doctor newDoctor = new Doctor(1L, "Valentin", "Herman");
        doctorService.addDoctor(newDoctor);
        verify(doctorRepository).save(newDoctor);
    }

    @Test
    void getAllDoctorsTest() {
        when(doctorRepository.findAll()).thenReturn(Stream.of(
                new Doctor(1L, "Valentin", "Herman"),
                new Doctor(2L, "Christophe", "Herman"))
                .toList());
        assertEquals(2, doctorService.getAllDoctors().size());
    }

    @Test
    void getDoctorByIdFoundTest(){
        Doctor doctor = new Doctor(1L, "Valentin", "Herman");
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        assertEquals(doctor, doctorService.getDoctorById(1L));
    }

    @Test
    void getDoctorByIdNotFoundTest(){
        when(doctorRepository.findById(2L)).thenReturn(Optional.empty());
        assertNull(doctorService.getDoctorById(2L));
    }

    @Test
    void deleteDoctorFoundTest(){
        when(doctorRepository.existsById(1L)).thenReturn(true);
        assertTrue(doctorService.deleteDoctor(1L));
        verify(doctorRepository).deleteById(1L);    
    }

    @Test
    void deleteDoctorNotFoundTest(){
        when(doctorRepository.existsById(2L)).thenReturn(false);
        assertFalse(doctorService.deleteDoctor(2L));
        verify(doctorRepository, never()).deleteById(2L);
    }

    @Test
    void updateDoctorFoundTest(){
        Doctor updateDoctor = new Doctor(null, "Ethan", "Loyens");
        when(doctorRepository.existsById(1L)).thenReturn(true);
        when(doctorRepository.save(updateDoctor)).thenReturn(updateDoctor);
        Doctor result = doctorService.updateDoctor(1L, updateDoctor);
        assertNotNull(result);
        assertEquals("Ethan", result.getFirstName());
        assertEquals("Loyens", result.getLastName());
    }

    @Test
    void updateDoctorNotFoundTest(){
        Doctor updateDoctor = new Doctor(null, "Pierre", "Barthélémy");
        when(doctorRepository.existsById(1l)).thenReturn(false);
        Doctor result = doctorService.updateDoctor(1L, updateDoctor);
        assertNull(result);
        verify(doctorRepository, never()).save(updateDoctor);
    }
}