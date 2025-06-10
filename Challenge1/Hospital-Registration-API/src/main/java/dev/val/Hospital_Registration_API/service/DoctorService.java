package dev.val.Hospital_Registration_API.service;

import dev.val.Hospital_Registration_API.model.Doctor;
import dev.val.Hospital_Registration_API.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public void addDoctor(Doctor newDoctor){
        doctorRepository.save(newDoctor);
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id){
        return doctorRepository.findById(id).orElse(null);
    }

    public boolean deleteDoctor(Long id){
        if(doctorRepository.existsById(id)){
            doctorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor){
        if(doctorRepository.existsById(id)){
            updatedDoctor.setId(id);
            return doctorRepository.save(updatedDoctor);
        }
        return null;
    }
}