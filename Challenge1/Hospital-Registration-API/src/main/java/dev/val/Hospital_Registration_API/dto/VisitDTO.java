package dev.val.Hospital_Registration_API.dto;

import java.time.LocalDate;

public record VisitDTO(
        Long id,
        String visitorName,
        String doctorName,
        LocalDate date ) {
}