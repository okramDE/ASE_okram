package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.web.dto.TimeUsageDto;
import com.example.zeitplaner.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService svc;
    public ReportController(ReportService svc) { this.svc = svc; }

    @GetMapping("/time-usage")
    public List<TimeUsageDto> getTimeUsage(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return svc.zeitnutzung(from, to);
    }
}