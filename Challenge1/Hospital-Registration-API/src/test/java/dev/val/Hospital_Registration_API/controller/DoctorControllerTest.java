package dev.val.Hospital_Registration_API.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;

import dev.val.Hospital_Registration_API.service.DoctorService;
import dev.val.Hospital_Registration_API.model.Doctor;

import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DoctorService doctorService;

    @Test
    void allDoctorsTest() throws Exception {
        List<Doctor> doctors = Stream.of(
                new Doctor(1L, "Valentin", "Herman"),
                new Doctor(2L, "Ethan", "Loyens")).toList();
        when(doctorService.getAllDoctors()).thenReturn(doctors);

        mockMvc.perform(get("/hospital-registration-api/v1/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Valentin"))
                .andExpect(jsonPath("$[1].firstName").value("Ethan"));
    }

    @Test
    void doctorFoundByIdTest() throws Exception {
        Doctor doctor = new Doctor(3L, "Henry", "Dupont");
        when(doctorService.getDoctorById(3L)).thenReturn(doctor);

        mockMvc.perform(get("/hospital-registration-api/v1/doctors/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.firstName").value("Henry"))
                .andExpect(jsonPath("$.lastName").value("Dupont"));
    }

    @Test
    void doctorNotFoundByIdTest() throws Exception {
        when(doctorService.getDoctorById(3L)).thenReturn(null);

        mockMvc.perform(get("/hospital-registration-api/v1/doctors/3"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Doctor not found!"));
    }

    @Test
    void addDoctorTest() throws Exception {
        Doctor doctor = new Doctor(null, "Henry", "Dupont");
        String requestBody = objectMapper.writeValueAsString(doctor);

        mockMvc.perform(post("/hospital-registration-api/v1/doctors")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Doctor created successfully!"));
    }

    @Test
    void updateDoctorFoundTest() throws Exception {
        Doctor doctor = new Doctor(2L, "Henry", "Dupont");
        String requestBody = objectMapper.writeValueAsString(doctor);
        when(doctorService.updateDoctor(2L, doctor)).thenReturn(doctor);

        mockMvc.perform(put("/hospital-registration-api/v1/doctors/2")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("Henry"))
                .andExpect(jsonPath("$.lastName").value("Dupont"));
    }

    @Test
    void updateDoctorNotFoundTest() throws Exception {
        Doctor doctor = new Doctor(2L, "Henry", "Dupont");
        String requestBody = objectMapper.writeValueAsString(doctor);
        when(doctorService.updateDoctor(2L, doctor)).thenReturn(null);

        mockMvc.perform(put("/hospital-registration-api/v1/doctors/2")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Doctor not found!"));
    }

    @Test
    void deleteDoctorFoundTest() throws Exception {
        when(doctorService.deleteDoctor(2L)).thenReturn(true);

        mockMvc.perform(delete("/hospital-registration-api/v1/doctors/2"))
               .andExpect(status().isNoContent());
    }

    @Test
    void deleteDoctorNotFoundTest() throws Exception {
        when(doctorService.deleteDoctor(2L)).thenReturn(false);

        mockMvc.perform(delete("/hospital-registration-api/v1/doctors/2"))
               .andExpect(status().isNotFound());
    }
}