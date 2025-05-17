package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.web.dto.TimeUsageDto;
import com.example.zeitplaner.domain.repository.TerminRepository;
import com.example.zeitplaner.domain.model.Termin;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final TerminRepository terminRepo;
    public ReportService(TerminRepository terminRepo) { this.terminRepo = terminRepo; }

    public List<TimeUsageDto> zeitnutzung(LocalDate from, LocalDate to) {
        var termine = terminRepo.findAll().stream()
                .filter(t -> !t.getStart().toLocalDate().isBefore(from)
                        && !t.getStart().toLocalDate().isAfter(to))
                .collect(Collectors.toList());

        // Gruppieren nach Kategorie-Entität und Summieren
        Map<Kategorie, Long> nutzung = termine.stream()
                .collect(Collectors.groupingBy(
                        Termin::getKategorie,
                        Collectors.summingLong(t ->
                                Duration.between(t.getStart(), t.getEnde()).toMinutes())));

        // Mapping auf neuen DTO mit ID + Name
        return nutzung.entrySet().stream()
                .map(e -> new TimeUsageDto(
                        e.getKey().getId(),
                        e.getKey().getName(),
                        e.getValue()))
                .collect(Collectors.toList());
    }
}