package dev.val.data_analysis_tool.model;

import java.time.LocalDate;

public record CovidData(
        String direction,
        LocalDate date,
        String country,
        String commodity,
        String transportMode,
        String measure,
        long value) {
}