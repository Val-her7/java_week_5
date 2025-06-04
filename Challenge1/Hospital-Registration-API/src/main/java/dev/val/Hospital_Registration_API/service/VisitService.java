package dev.val.Hospital_Registration_API.service;

import org.springframework.stereotype.Service;
import dev.val.Hospital_Registration_API.model.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private List<Visit> visits = new ArrayList<>();

    public void addVisit(Visit newVisit) {
        visits.add(newVisit);
    }

    public List<Visit> getAllVisits() {
        return visits;
    }

    public List<Visit> getAllVisitsByDate(LocalDate date) {
        return visits.stream()
                .filter(v -> v.getTimestamp().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Visit> getAllVisitsByPeriod(LocalDate start, LocalDate end) {
        return visits.stream()
                .filter(v -> (v.getTimestamp().toLocalDate().equals(start)
                        || v.getTimestamp().toLocalDate().isAfter(start))
                        && (v.getTimestamp().toLocalDate().equals(end) || v.getTimestamp().toLocalDate().isBefore(end)))
                .collect(Collectors.toList());
    }
}