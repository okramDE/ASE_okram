package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.service.KategorieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kategorien")
public class KategorieController {
    private final KategorieService svc;

    public KategorieController(KategorieService svc) {
        this.svc = svc;
    }

    @PostMapping
    public Kategorie create(@RequestBody Kategorie k) {
        return svc.create(k);
    }

    @GetMapping
    public List<Kategorie> list() {
        return svc.list();
    }

    @GetMapping("/{id}")
    public Kategorie getById(@PathVariable Long id) {
        return svc.getById(id);
    }

    @PutMapping("/{id}")
    public Kategorie update(@PathVariable Long id, @RequestBody Kategorie k) {
        return svc.update(id, k);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}