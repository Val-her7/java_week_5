package dev.val.Hospital_Registration_API.controller;

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
    public List<Doctor> allDoctors(){
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Doctor doctorById(@PathVariable Long id){
        return doctorService.getDoctorById(id);
    }

    @PostMapping
    public void addDoctor(@RequestBody Doctor newDoctor){
        doctorService.addDoctor(newDoctor);
    }

    @PutMapping("/{id}")
    public void updateDoctor(@PathVariable Long id, @RequestBody Doctor updateDoctor){
        doctorService.updateDoctor(id, updateDoctor);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctor(id);
    }
}