package dev.val.Hospital_Registration_API.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.val.Hospital_Registration_API.service.DoctorService;
import java.util.List;
import dev.val.Hospital_Registration_API.model.Doctor;

@RestController
@CrossOrigin
@RequestMapping("/hospital-registration-api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> allDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> doctorById(@PathVariable Long id){
        Doctor doctor = doctorService.getDoctorById(id);

        if(doctor == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found!");
        }
        return ResponseEntity.ok(doctor);
    }

    @PostMapping
    public ResponseEntity<String> addDoctor(@RequestBody Doctor newDoctor){
        doctorService.addDoctor(newDoctor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDoctor(@PathVariable Long id, @RequestBody Doctor updateDoctor){
        Doctor updated = doctorService.updateDoctor(id, updateDoctor);

        if(updated == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found!");
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        boolean deleted = doctorService.deleteDoctor(id);

        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}