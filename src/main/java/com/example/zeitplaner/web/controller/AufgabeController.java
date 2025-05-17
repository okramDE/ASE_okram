package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Aufgabe;
import com.example.zeitplaner.service.AufgabeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/aufgaben")
public class AufgabeController {
    private final AufgabeService svc;
    public AufgabeController(AufgabeService svc) { this.svc = svc; }

    // CREATE
    @PostMapping
    public Aufgabe create(@RequestBody Aufgabe a) {
        return svc.legeAufgabeAn(a);
    }

    // READ ALL
    @GetMapping
    public List<Aufgabe> list() {
        return svc.getAufgabenSortiert();
    }

    // READ by ID
    @GetMapping("/{id}")
    public Aufgabe getById(@PathVariable Long id) {
        return svc.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Aufgabe update(@PathVariable Long id,
                          @RequestBody Aufgabe a) {
        return svc.updateAufgabe(id, a);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        svc.deleteAufgabe(id);
    }

    // SEARCH by deadline range
    @GetMapping("/search")
    public List<Aufgabe> searchByDeadline(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to
    ) {
        return svc.sucheNachDeadline(from, to);
    }
}
