package dev.val.Hospital_Registration_API.service;

import org.springframework.stereotype.Service;

import dev.val.Hospital_Registration_API.dto.VisitDTO;
import dev.val.Hospital_Registration_API.mapper.VisitDTOMapper;
import dev.val.Hospital_Registration_API.model.Visit;
import dev.val.Hospital_Registration_API.repository.VisitRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitDTOMapper visitDTOMapper;

    public VisitService(VisitRepository visitRepository, VisitDTOMapper visitDTOMapper) {
        this.visitRepository = visitRepository;
        this.visitDTOMapper = visitDTOMapper;
    }

    public void addVisit(Visit newVisit) {
        visitRepository.save(newVisit);
    }

    public List<VisitDTO> getAllVisits() {
        return visitRepository.findAll().stream().map(visitDTOMapper).toList();
    }

    public List<Visit> getAllVisitsByDate(LocalDate date) {
        return visitRepository.findAll().stream()
                .filter(v -> v.getTimestamp().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Visit> getAllVisitsByPeriod(LocalDate start, LocalDate end) {
        return visitRepository.findAll().stream()
                .filter(v -> (v.getTimestamp().toLocalDate().equals(start)
                        || v.getTimestamp().toLocalDate().isAfter(start))
                        && (v.getTimestamp().toLocalDate().equals(end) || v.getTimestamp().toLocalDate().isBefore(end)))
                .collect(Collectors.toList());
    }
}