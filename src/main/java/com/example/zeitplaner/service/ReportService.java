package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.web.dto.TimeUsageDto;
import com.example.zeitplaner.domain.repository.TerminRepository;
import com.example.zeitplaner.domain.model.Termin;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final TerminRepository terminRepo;
    public ReportService(TerminRepository terminRepo) { this.terminRepo = terminRepo; }

    public List<TimeUsageDto> zeitnutzung(LocalDateTime from, LocalDateTime to) {
        var termine = terminRepo.findAll().stream()
                .filter(t -> !t.getStart().toLocalDate().isBefore(ChronoLocalDate.from(from))
                        && !t.getStart().toLocalDate().isAfter(ChronoLocalDate.from(to)))
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

    public List<TimeUsageDto> zeitnutzungKategorieName(String name, LocalDateTime von, LocalDateTime bis) {
        // Beispiel: Hole alle Termine einer Kategorie nach Name und Zeitbereich
        var termine = terminRepo.findByKategorie_Name(name).stream()
                .filter(t -> !t.getStart().isBefore(von) && !t.getStart().isAfter(bis))
                .collect(Collectors.toList());

        // Zeit aufsummieren wie im normalen Report
        long min = termine.stream()
                .mapToLong(t -> Duration.between(t.getStart(), t.getEnde()).toMinutes())
                .sum();

        // DTO erzeugen (nur eine Kategorie!)
        if (termine.isEmpty()) return List.of();

        Kategorie kat = termine.get(0).getKategorie();
        return List.of(new TimeUsageDto(kat.getId(), kat.getName(), min));
    }
}