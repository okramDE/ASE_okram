package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.service.TerminService;
import com.example.zeitplaner.web.dto.TerminDto;
import com.example.zeitplaner.web.mapper.TerminMapper;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/termine")
public class TerminController {

    private final TerminService svc;
    private final TerminMapper mapper;

    public TerminController(TerminService svc, TerminMapper mapper) {
        this.svc    = svc;
        this.mapper = mapper;
    }

    // CREATE → nimmt TerminDto, gibt Liste<TerminDto> zurück
    @PostMapping
    public ResponseEntity<List<TerminDto>> erstelleTermin(
            @Valid @RequestBody TerminDto dto
    ) {
        List<Termin> gespeicherte = svc.legeTerminAn(mapper.dtoZuEntity(dto));
        List<TerminDto> out = gespeicherte.stream()
                .map(mapper::entityZuDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    // READ ALL → Liste von TerminDto
    @GetMapping
    public List<TerminDto> alleTermine() {
        return svc.alleTermine().stream()
                .map(mapper::entityZuDto)
                .collect(Collectors.toList());
    }

    // READ by ID → TerminDto
    @GetMapping("/{id}")
    public TerminDto holeTermin(@PathVariable Long id) {
        return mapper.entityZuDto(svc.getById(id));
    }

    // UPDATE → nimmt TerminDto, gibt TerminDto
    @PutMapping("/{id}")
    public TerminDto aktualisiereTermin(
            @PathVariable Long id,
            @Valid @RequestBody TerminDto dto
    ) {
        Termin updated = svc.updateTermin(id, mapper.dtoZuEntity(dto));
        return mapper.entityZuDto(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loescheTermin(@PathVariable Long id) {
        svc.deleteTermin(id);
    }

    // SEARCH nach Kategorie-ID
    @GetMapping("/kategorie/{kategorieId}")
    public List<TerminDto> termineNachKategorieId(@PathVariable Long kategorieId) {
        return svc.sucheNachKategorieId(kategorieId).stream()
                .map(mapper::entityZuDto)
                .collect(Collectors.toList());
    }

    // SEARCH nach Zeitraum
    @GetMapping("/suche")
    public List<TerminDto> suche(
            @RequestParam(required = false) Long kategorieId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime von,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime bis
    ) {
        if (kategorieId != null) {
            return termineNachKategorieId(kategorieId);
        }
        if (von != null && bis != null) {
            return svc.sucheNachZeitraum(von, bis).stream()
                    .map(mapper::entityZuDto)
                    .collect(Collectors.toList());
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Bitte 'kategorieId' oder 'von' und 'bis' angeben");
    }
}
