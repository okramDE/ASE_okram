package com.example.zeitplaner.service;


import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.domain.repository.KategorieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class KategorieService {
    private final KategorieRepository repo;

    public KategorieService(KategorieRepository repo) {
        this.repo = repo;
    }

    public Kategorie create(Kategorie k) {
        if (repo.existsByName(k.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Kategorie existiert bereits");
        }
        return repo.save(k);
    }

    public List<Kategorie> list() {
        return repo.findAll();
    }

    public Kategorie getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategorie nicht gefunden"));
    }

    public Kategorie update(Long id, Kategorie k) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setName(k.getName());
                    return repo.save(existing);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategorie nicht gefunden"));
    }

    public Kategorie getByName(String name) {
        return repo.findByName(name);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategorie nicht gefunden");
        }
        repo.deleteById(id);
    }
}