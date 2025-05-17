package com.example.zeitplaner.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeUsageDto {
    private Long kategorieId;
    private String kategorieName;
    private long minuten;
}