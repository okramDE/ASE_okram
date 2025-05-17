// src/main/java/com/example/zeitplaner/web/controller/TerminController.java
package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.service.KategorieService;
import com.example.zeitplaner.service.TerminService;
import com.example.zeitplaner.web.dto.TerminDto;
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

    private final TerminService terminService;
    private final KategorieService kategorieService;

    public TerminController(TerminService terminService,
                            KategorieService kategorieService) {
        this.terminService    = terminService;
        this.kategorieService = kategorieService;
    }

    @PostMapping
    public ResponseEntity<List<TerminDto>> erstelleTermin(
            @Valid @RequestBody TerminDto dto
    ) {
        Kategorie kat = kategorieService.getById(dto.getKategorieId());
        Termin basis = new Termin();
        basis.setStart(dto.getBeginn());
        basis.setEnde(dto.getEnde());
        basis.setTitel(dto.getTitel());
        basis.setKategorie(kat);
        basis.setWiederholungsRegel(dto.getWiederholungsRegel());

        List<Termin> gespeicherte = terminService.legeTerminAn(basis);

        List<TerminDto> out = gespeicherte.stream().map(t -> {
            TerminDto o = new TerminDto();
            o.setId(t.getId());
            o.setBeginn(t.getStart());
            o.setEnde(t.getEnde());
            o.setTitel(t.getTitel());
            o.setKategorieId(t.getKategorie().getId());
            o.setWiederholungsRegel(t.getWiederholungsRegel());
            return o;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @GetMapping
    public List<TerminDto> alleTermine() {
        return terminService.alleTermine().stream().map(t -> {
            TerminDto o = new TerminDto();
            o.setId(t.getId());
            o.setBeginn(t.getStart());
            o.setEnde(t.getEnde());
            o.setTitel(t.getTitel());
            o.setKategorieId(t.getKategorie().getId());
            o.setWiederholungsRegel(t.getWiederholungsRegel());
            return o;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TerminDto holeTermin(@PathVariable Long id) {
        Termin t = terminService.getById(id);
        TerminDto o = new TerminDto();
        o.setId(t.getId());
        o.setBeginn(t.getStart());
        o.setEnde(t.getEnde());
        o.setTitel(t.getTitel());
        o.setKategorieId(t.getKategorie().getId());
        o.setWiederholungsRegel(t.getWiederholungsRegel());
        return o;
    }

    @PutMapping("/{id}")
    public TerminDto aktualisiereTermin(
            @PathVariable Long id,
            @Valid @RequestBody TerminDto dto
    ) {
        Kategorie kat = kategorieService.getById(dto.getKategorieId());
        Termin neu = new Termin();
        neu.setStart(dto.getBeginn());
        neu.setEnde(dto.getEnde());
        neu.setTitel(dto.getTitel());
        neu.setKategorie(kat);
        neu.setWiederholungsRegel(dto.getWiederholungsRegel());

        Termin updated = terminService.updateTermin(id, neu);
        TerminDto o = new TerminDto();
        o.setId(updated.getId());
        o.setBeginn(updated.getStart());
        o.setEnde(updated.getEnde());
        o.setTitel(updated.getTitel());
        o.setKategorieId(updated.getKategorie().getId());
        o.setWiederholungsRegel(updated.getWiederholungsRegel());
        return o;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loescheTermin(@PathVariable Long id) {
        terminService.deleteTermin(id);
    }

    @GetMapping("/kategorie/{kategorieId}")
    public List<TerminDto> termineNachKategorie(@PathVariable Long kategorieId) {
        return terminService.sucheNachKategorieId(kategorieId).stream().map(t -> {
            TerminDto o = new TerminDto();
            o.setId(t.getId());
            o.setBeginn(t.getStart());
            o.setEnde(t.getEnde());
            o.setTitel(t.getTitel());
            o.setKategorieId(t.getKategorie().getId());
            o.setWiederholungsRegel(t.getWiederholungsRegel());
            return o;
        }).collect(Collectors.toList());
    }

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
        List<Termin> ergebnis;
        if (kategorieId != null) {
            ergebnis = terminService.sucheNachKategorieId(kategorieId);
        } else if (von != null && bis != null) {
            ergebnis = terminService.sucheNachZeitraum(von, bis);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Bitte 'kategorieId' oder 'von' und 'bis' angeben");
        }
        return ergebnis.stream().map(t -> {
            TerminDto o = new TerminDto();
            o.setId(t.getId());
            o.setBeginn(t.getStart());
            o.setEnde(t.getEnde());
            o.setTitel(t.getTitel());
            o.setKategorieId(t.getKategorie().getId());
            o.setWiederholungsRegel(t.getWiederholungsRegel());
            return o;
        }).collect(Collectors.toList());
    }
}
