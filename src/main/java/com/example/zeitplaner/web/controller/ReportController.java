// src/main/java/com/example/zeitplaner/web/controller/ReportController.java
package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.web.dto.TimeUsageDto;
import com.example.zeitplaner.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    //GET http://localhost:8080/api/report/time-usage?von=2025-06-01T00:00:00&bis=2025-06-20T23:59:59
    @GetMapping("/time-usage")
    public List<TimeUsageDto> holeZeitnutzung(
            @RequestParam("von") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime von,
            @RequestParam("bis") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bis
    ) {
        return reportService.zeitnutzung(von, bis);
    }

    @GetMapping("/time-usage-name")
    public List<TimeUsageDto> holeZeitnutzungName(
            @RequestParam("name") String name,
            @RequestParam("von") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime von,
            @RequestParam("bis") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bis) {
        return reportService.zeitnutzungKategorieName(name, von, bis);
    }
}
