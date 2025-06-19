package dev.val.data_analysis_tool.controller;

import dev.val.data_analysis_tool.service.CovidDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/covid_and_trade/api/v1/stats")
public class CovidDataController {

    private final CovidDataService covidDataService;

    public CovidDataController(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    @GetMapping("/monthly/total/{year}/{month}")
    public ResponseEntity<Long> getMonthlyTotal(
            @PathVariable int year,
            @PathVariable int month,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String commodity,
            @RequestParam(required = false) String transportMode,
            @RequestParam(required = false) String measure
    ) {
        return ResponseEntity.ok(covidDataService.getMonthlyTotal(month, year, country, commodity, transportMode, measure));
    }

    @GetMapping("/monthly/average/{year}/{month}")
    public ResponseEntity<Double> getMonthlyAverage(
            @PathVariable int year,
            @PathVariable int month,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String commodity,
            @RequestParam(required = false) String transportMode,
            @RequestParam(required = false) String measure
    ) {
        return ResponseEntity.ok(covidDataService.getMonthlyAverage(month, year, country, commodity, transportMode, measure));
    }

    @GetMapping("yearly/total/{year}")
    public ResponseEntity<Map<String,Long>> getYearlyTotal(
            @PathVariable int year,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String commodity,
            @RequestParam(required = false) String transportMode,
            @RequestParam(required = false) String measure
    ) {
        return ResponseEntity.ok(covidDataService.getYearlyTotal(year, country, commodity, transportMode, measure));
    }

    @GetMapping("yearly/average/{year}")
    public ResponseEntity<Map<String,Double>> getYearlyAverage(
            @PathVariable int year,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String commodity,
            @RequestParam(required = false) String transportMode,
            @RequestParam(required = false) String measure
    ) {
        return ResponseEntity.ok(covidDataService.getYearlyAverage(year, country, commodity, transportMode, measure));
    }
}