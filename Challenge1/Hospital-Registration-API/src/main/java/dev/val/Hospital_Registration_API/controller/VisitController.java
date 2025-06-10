package dev.val.Hospital_Registration_API.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.val.Hospital_Registration_API.service.VisitService;

import java.time.LocalDate;
import java.util.List;

import dev.val.Hospital_Registration_API.dto.VisitDTO;
import dev.val.Hospital_Registration_API.model.Visit;

@RestController
@CrossOrigin
@RequestMapping("/hospital-registration-api/v1/visits")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public ResponseEntity<List<VisitDTO>> allVisits() {
        return ResponseEntity.ok(visitService.getAllVisits());
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<Visit>> allVisitsByDate(@RequestParam(value = "date") LocalDate date) {
        return ResponseEntity.ok(visitService.getAllVisitsByDate(date));
    }

    @GetMapping("/by-period")
    public ResponseEntity<List<Visit>> allVisitsByPeriod(@RequestParam(value = "start") LocalDate start,
            @RequestParam(value = "end") LocalDate end) {
        return ResponseEntity.ok(visitService.getAllVisitsByPeriod(start, end));
    }

    @PostMapping
    public ResponseEntity<String> addVisit(@RequestBody Visit newVisit){
        visitService.addVisit(newVisit);
        return ResponseEntity.status(HttpStatus.CREATED).body("Visit created successfully!");
    }
}