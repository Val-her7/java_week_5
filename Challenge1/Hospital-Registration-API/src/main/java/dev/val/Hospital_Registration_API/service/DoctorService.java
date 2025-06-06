package dev.val.Hospital_Registration_API.service;

import java.util.ArrayList;
import dev.val.Hospital_Registration_API.model.Doctor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private List<Doctor> doctors = new ArrayList<>();

    public void addDoctor(Doctor newDoctor){
        doctors.add(newDoctor);
    }

    public List<Doctor> getAllDoctors(){
        return doctors;
    }

    public Doctor getDoctorById(Long id){
        return doctors.stream()
                .filter(d -> d.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public boolean deleteDoctor(Long id){
        return doctors.removeIf(d -> d.getId().equals(id));
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor){
        for(int i = 0; i < doctors.size(); i++){
            if(doctors.get(i).getId().equals(id)){
                doctors.set(i, updatedDoctor);
                return updatedDoctor;
            }
        }
        return null;
    }
}