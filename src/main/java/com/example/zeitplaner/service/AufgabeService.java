package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Aufgabe;
import com.example.zeitplaner.domain.repository.AufgabeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AufgabeService {
    private final AufgabeRepository repo;
    public AufgabeService(AufgabeRepository repo) { this.repo = repo; }

    public Aufgabe legeAufgabeAn(Aufgabe a) {
        return repo.save(a);
    }

    public List<Aufgabe> getAufgabenSortiert() {
        return repo.findAllByOrderByPrioritaetDescDeadlineAsc();
    }

    public Aufgabe updateAufgabe(Long id, Aufgabe update) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setTitel(update.getTitel());
                    existing.setDeadline(update.getDeadline());
                    existing.setPrioritaet(update.getPrioritaet());
                    existing.setKategorie(update.getKategorie());
                    return repo.save(existing);
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Aufgabe nicht gefunden"));
    }

    public void deleteAufgabe(Long id) {
        throwIfNotFound(repo.existsById(id), "Aufgabe nicht gefunden");
        repo.deleteById(id);
    }

    public List<Aufgabe> sucheNachDeadline(LocalDateTime from, LocalDateTime to) {
        return repo.findByDeadlineBetween(from, to);
    }

    public Aufgabe getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aufgabe nicht gefunden"));
    }

    private void throwIfNotFound(boolean exists, String msg) {
        if (!exists) throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
    }

}
