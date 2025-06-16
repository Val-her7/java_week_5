package dev.val.API_Client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiClientApplication.class, args);

		DoctorClient doctorClient = new DoctorClient();
		doctorClient.addNewDoctor(new Doctor(null, "Doctor", "A"));
		doctorClient.addNewDoctor(new Doctor(null, "Doctor", "B"));
		doctorClient.addNewDoctor(new Doctor(null, "Doctor", "C"));

		System.out.println(doctorClient.getAllDoctors());
		System.out.println(doctorClient.getDoctorById(2L));
	}

}