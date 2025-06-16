package dev.val.API_Client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class DoctorClient {

    private RestClient restClient;

    public DoctorClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080/hospital-registration-api/v1")
                .build();
    }

    public List<Doctor> getAllDoctors() {
        return restClient.get()
                .uri("/doctors")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Doctor>>() {
                });
    }

    public Doctor getDoctorById(Long id) {
        return restClient.get()
                .uri("/doctors/{id}", id)
                .retrieve()
                .body(Doctor.class);
    }

    public String addNewDoctor(Doctor newDoctor) {
        return restClient.post()
                .uri("/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .body(newDoctor)
                .retrieve()
                .body(String.class);
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        return restClient.put()
                .uri("/doctors/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedDoctor)
                .retrieve()
                .body(Doctor.class);
    }

    public void deleteDoctor(Long id) {
        restClient.delete()
                .uri("/doctors/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}