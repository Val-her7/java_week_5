package dev.val.Hospital_Registration_API.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Visit {

    private String name;
    private String lastName;
    private String reasonOfRegistration;
    private LocalDateTime timestamp;
}