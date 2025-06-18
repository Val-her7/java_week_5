package dev.val.data_analysis_tool.service;

import dev.val.data_analysis_tool.model.CovidData;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidDataService {

    private List<CovidData> covidDataList = new ArrayList<>();

    @PostConstruct
    public void init() {
        readFile();
    }

    public void readFile() {
        InputStream file = getClass().getResourceAsStream("/static/covid_and_trade.csv");
        if (file == null) {
            System.out.println("File not found.");
            return;
        }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file))){

            reader.readLine();
            String line;

            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length == 10){
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
        }
        catch (IOException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
    }
}