package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.service.TerminService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/termine")
public class TerminController {
    private final TerminService svc;
    public TerminController(TerminService svc) { this.svc = svc; }

    // CREATE
    @PostMapping
    public List<Termin> create(@RequestBody Termin t) {
        return svc.legeTerminAn(t);
    }

    // READ ALL
    @GetMapping
    public List<Termin> list() {
        return svc.alleTermine();
    }

    // READ by ID
    @GetMapping("/{id}")
    public Termin getById(@PathVariable Long id) {
        return svc.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Termin update(@PathVariable Long id,
                         @RequestBody Termin t) {
        return svc.updateTermin(id, t);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        svc.deleteTermin(id);
    }

    //  Suche nach Kategorie-ID ---
    // GET /api/termine/kategorie/{id}
    @GetMapping("/kategorie/{id}")
    public List<Termin> termineNachKategorieId(@PathVariable("id") Long kategorieId) {
        return svc.sucheNachKategorieId(kategorieId);
    }

    // Suche nach Kategorie-Name ---
    // GET /api/termine/kategorie/name/{name}
    @GetMapping("/kategorie/name/{name}")
    public List<Termin> termineNachKategorieName(@PathVariable("name") String name) {
        return svc.sucheNachKategorieName(name);
    }

    // --- optionaler kombi-Endpoint für Suche nach Zeitraum oder Kategorie ---
    // GET /api/termine/suche?kategorieId=1
    // GET /api/termine/suche?kategorieName=Arbeit
    // GET /api/termine/suche?von=2025-05-01T00:00&bis=2025-05-31T23:59
    @GetMapping("/suche")
    public List<Termin> suche(
            @RequestParam(required = false) Long kategorieId,
            @RequestParam(required = false) String kategorieName,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime von,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime bis
    ) {
        if (kategorieId != null) {
            return svc.sucheNachKategorieId(kategorieId);
        }
        if (kategorieName != null) {
            return svc.sucheNachKategorieName(kategorieName);
        }
        if (von != null && bis != null) {
            return svc.sucheNachZeitraum(von, bis);
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Bitte 'kategorieId' oder 'kategorieName' oder beide 'von' und 'bis' angeben");
    }

}