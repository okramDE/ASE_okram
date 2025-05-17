// src/main/java/com/example/zeitplaner/web/controller/KategorieController.java
package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.service.KategorieService;
import com.example.zeitplaner.web.dto.KategorieDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kategorien")
public class KategorieController {

    private final KategorieService kategorieService;

    public KategorieController(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    @PostMapping
    public KategorieDto erstelleKategorie(
            @Valid @RequestBody KategorieDto dto
    ) {
        Kategorie k = new Kategorie();
        k.setName(dto.getName());
        Kategorie saved = kategorieService.create(k);

        KategorieDto out = new KategorieDto();
        out.setId(saved.getId());
        out.setName(saved.getName());
        return out;
    }

    @GetMapping
    public List<KategorieDto> alleKategorien() {
        return kategorieService.list().stream().map(k -> {
            KategorieDto o = new KategorieDto();
            o.setId(k.getId());
            o.setName(k.getName());
            return o;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public KategorieDto holeKategorie(@PathVariable Long id) {
        Kategorie k = kategorieService.getById(id);
        KategorieDto o = new KategorieDto();
        o.setId(k.getId());
        o.setName(k.getName());
        return o;
    }

    @PutMapping("/{id}")
    public KategorieDto aktualisiereKategorie(
            @PathVariable Long id,
            @Valid @RequestBody KategorieDto dto
    ) {
        Kategorie neu = new Kategorie();
        neu.setName(dto.getName());
        Kategorie updated = kategorieService.update(id, neu);

        KategorieDto o = new KategorieDto();
        o.setId(updated.getId());
        o.setName(updated.getName());
        return o;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loescheKategorie(@PathVariable Long id) {
        kategorieService.delete(id);
    }
}
