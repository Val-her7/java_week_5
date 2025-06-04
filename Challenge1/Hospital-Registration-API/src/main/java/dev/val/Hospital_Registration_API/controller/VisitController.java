package dev.val.Hospital_Registration_API.controller;

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
    public List<Visit> allVisits() {
        return visitService.getAllVisits();
    }

    @GetMapping(params = "date")
    public List<Visit> allVisitsByDate(@RequestParam(value = "date") LocalDate date) {
        return visitService.getAllVisitsByDate(date);
    }

    @GetMapping(params = { "start", "end" })
    public List<Visit> allVisitsByPeriod(@RequestParam(value = "start") LocalDate start,
            @RequestParam(value = "end") LocalDate end) {
        return visitService.getAllVisitsByPeriod(start, end);
    }

    @PostMapping
    public void addVisit(@RequestBody Visit newVisit){
        visitService.addVisit(newVisit);
    }
}