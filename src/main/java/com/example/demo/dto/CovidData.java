package com.example.demo.dto;

import lombok.Data;

@Data
public class CovidData {
    private String location;
    private String date;
    private int totalCases;
    private int newCases;
    private int totalDeaths;
    private int newDeaths;
    private int totalCasesPeMillion;
}
