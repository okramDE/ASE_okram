package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Aufgabe;
import com.example.zeitplaner.service.AufgabeService;
import com.example.zeitplaner.web.dto.AufgabeDto;
import com.example.zeitplaner.web.mapper.AufgabeMapper;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aufgaben")
public class AufgabeController {

    private final AufgabeService svc;
    private final AufgabeMapper mapper;

    public AufgabeController(AufgabeService svc, AufgabeMapper mapper) {
        this.svc    = svc;
        this.mapper = mapper;
    }

    @PostMapping
    public AufgabeDto erstelleAufgabe(@Valid @RequestBody AufgabeDto dto) {
        Aufgabe a = svc.legeAufgabeAn(mapper.dtoZuEntity(dto));
        return mapper.entityZuDto(a);
    }

    @GetMapping
    public List<AufgabeDto> alleAufgaben() {
        return svc.getAufgabenSortiert().stream()
                .map(mapper::entityZuDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AufgabeDto holeAufgabe(@PathVariable Long id) {
        return mapper.entityZuDto(svc.getById(id));
    }

    @PutMapping("/{id}")
    public AufgabeDto aktualisiereAufgabe(
            @PathVariable Long id,
            @Valid @RequestBody AufgabeDto dto
    ) {
        Aufgabe updated = svc.updateAufgabe(id, mapper.dtoZuEntity(dto));
        return mapper.entityZuDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loescheAufgabe(@PathVariable Long id) {
        svc.deleteAufgabe(id);
    }

    @GetMapping("/suche")
    public List<AufgabeDto> sucheNachDeadline(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime von,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime bis
    ) {
        return svc.sucheNachDeadline(von, bis).stream()
                .map(mapper::entityZuDto)
                .collect(Collectors.toList());
    }
}
