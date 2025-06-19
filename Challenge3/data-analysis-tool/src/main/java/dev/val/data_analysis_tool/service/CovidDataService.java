package dev.val.data_analysis_tool.service;

import dev.val.data_analysis_tool.model.CovidData;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CovidDataService {

    private List<CovidData> covidDataList = new ArrayList<>();

    @PostConstruct
    public void init() {
        readFile();
    }

    public void readFile() {
        InputStream file = getClass().getResourceAsStream("/csv/covid_and_trade.csv");
        if (file == null) {
            System.out.println("File not found.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {

            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 10) {
                    covidDataList.add(new CovidData(
                            parts[0],
                            LocalDate.parse(parts[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            parts[4],
                            parts[5],
                            parts[6],
                            parts[7],
                            Long.parseLong(parts[8])
                    ));
                }
            }
            System.out.println(covidDataList);
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
    }

    public Long getMonthlyTotal(int month, int year, String country, String commodity, String transportMode, String measure) {
        return covidDataList.stream()
                .filter(row -> (row.direction().equals("Imports")
                        || row.direction().equals("Exports")) &&
                        row.date().getMonthValue() == month &&
                        row.date().getYear() == year &&
                        (country == null || row.country().equalsIgnoreCase(country)) &&
                        (commodity == null || row.commodity().equalsIgnoreCase(commodity)) &&
                        (transportMode == null || row.transportMode().equalsIgnoreCase(transportMode)) &&
                        (measure == null || row.measure().equalsIgnoreCase(measure)))
                .mapToLong(row -> row.value())
                .sum();
    }

    public Double getMonthlyAverage(int month, int year, String country, String commodity, String transportMode, String measure) {
        return covidDataList.stream()
                .filter(row -> (row.direction().equals("Imports")
                        || row.direction().equals("Exports")) &&
                        row.date().getMonthValue() == month &&
                        row.date().getYear() == year &&
                        (country == null || row.country().equalsIgnoreCase(country)) &&
                        (commodity == null || row.commodity().equalsIgnoreCase(commodity)) &&
                        (transportMode == null || row.transportMode().equalsIgnoreCase(transportMode)) &&
                        (measure == null || row.measure().equalsIgnoreCase(measure)))
                .mapToLong(row -> row.value())
                .average()
                .orElse(0);
    }

    public Map<String, Long> getYearlyTotal(int year, String country, String commodity, String transportMode, String measure) {
        return covidDataList.stream()
                .filter(row -> (row.direction().equals("Imports")
                        || row.direction().equals("Exports")) &&
                        row.date().getYear() == year &&
                        (country == null || row.country().equalsIgnoreCase(country)) &&
                        (commodity == null || row.commodity().equalsIgnoreCase(commodity)) &&
                        (transportMode == null || row.transportMode().equalsIgnoreCase(transportMode)) &&
                        (measure == null || row.measure().equalsIgnoreCase(measure)))
                .collect(Collectors.groupingBy(
                        row -> row.date().getMonth().toString(),
                        LinkedHashMap::new,
                        Collectors.summingLong(CovidData::value)));
    }

    public Map<String, Double> getYearlyAverage(int year, String country, String commodity, String transportMode, String measure) {
        return covidDataList.stream()
                .filter(row -> (row.direction().equals("Imports")
                        || row.direction().equals("Exports")) &&
                        row.date().getYear() == year &&
                        (country == null || row.country().equalsIgnoreCase(country)) &&
                        (commodity == null || row.commodity().equalsIgnoreCase(commodity)) &&
                        (transportMode == null || row.transportMode().equalsIgnoreCase(transportMode)) &&
                        (measure == null || row.measure().equalsIgnoreCase(measure)))
                .collect(Collectors.groupingBy(
                        row -> row.date().getMonth().toString(),
                        LinkedHashMap::new,
                        Collectors.averagingLong(CovidData::value)));
    }
}