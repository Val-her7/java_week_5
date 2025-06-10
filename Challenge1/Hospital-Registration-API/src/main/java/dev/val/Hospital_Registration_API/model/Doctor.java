package dev.val.Hospital_Registration_API.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Doctor {

    private Long id;
    private String firstName;
    private String lastName;
}