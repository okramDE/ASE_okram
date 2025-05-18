package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.domain.repository.TerminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TerminService {
    private final TerminRepository repo;
    private final WiederholungsRegelService wiederholungsRegelService;
    private final KollisionsService kollisionsService;

    public TerminService(TerminRepository repo, WiederholungsRegelService wiederholungsRegelService, KollisionsService kollisionsService) {
        this.repo = repo;
        this.wiederholungsRegelService = wiederholungsRegelService;
        this.kollisionsService = kollisionsService;
    }

    public List<Termin> legeTerminAn(Termin t) {
        // 1) expandiere Basis-Termin zu allen Wiederholungen
        List<Termin> batch = wiederholungsRegelService.expandRecurrence(t);
        List<Termin> saved = new ArrayList<>();
        for (Termin term : batch) {
            // 2) Kollision prüfen
            if (kollisionsService.hatKollision(term)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Kollision bei " + term.getStart());
            }
            saved.add(repo.save(term));
        }
        return saved;
    }


    public List<Termin> alleTermine() { return repo.findAll(); }

    // Update
    public Termin updateTermin(Long id, Termin update) {
        return repo.findById(id)
                .map(existing -> {
                    // Kollisionen ignorieren, wenn es derselbe Termin ist:
                    if (kollisionsService.hatKollision(update)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Kollision bei " + update.getStart());
                    }
                    // Validierung
                    if (update.getStart().isAfter(update.getEnde()) || update.getStart().isEqual(update.getEnde())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start muss vor Ende liegen");
                    }
                    if (update.getKategorie() == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kategorie fehlt");
                    }
                    // Felder mappen
                    existing.setStart(update.getStart());
                    existing.setEnde(update.getEnde());
                    existing.setTitel(update.getTitel());
                    existing.setKategorie(update.getKategorie());
                    existing.setWiederholungsRegel(update.getWiederholungsRegel());
                    return repo.save(existing);
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Termin nicht gefunden"));
    }


    // Delete
    public void deleteTermin(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Termin nicht gefunden");
        }
        repo.deleteById(id);
    }

    // Suche nach Kategorie
    public List<Termin> sucheNachKategorieId(Long id) {
        return repo.findByKategorie_Id(id);
    }
    public List<Termin> sucheNachKategorieName(String name) {
        return repo.findByKategorie_Name(name);
    }

    // Suche nach Datum
    public List<Termin> sucheNachZeitraum(LocalDateTime from, LocalDateTime to) {
        return repo.findByStartBetween(from, to);
    }
    // Suche nach id
    public Termin getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Termin nicht gefunden"));
    }

    public List<Termin> sucheNachKategorieUndZeitraum(LocalDateTime von, LocalDateTime bis) {
        // Beispiel-Implementierung, je nach Repo:
        return repo.findByStartBetween(von, bis);
    }
}