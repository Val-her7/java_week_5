package dev.val.API_Client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Doctor {
    private Long id;
    private String firstName;
    private String lastName;
}