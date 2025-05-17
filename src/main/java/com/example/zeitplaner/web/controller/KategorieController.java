package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.service.KategorieService;
import com.example.zeitplaner.web.dto.KategorieDto;
import com.example.zeitplaner.web.mapper.KategorieMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kategorien")
public class KategorieController {

    private final KategorieService svc;
    private final KategorieMapper mapper;

    public KategorieController(KategorieService svc, KategorieMapper mapper) {
        this.svc    = svc;
        this.mapper = mapper;
    }

    @PostMapping
    public KategorieDto erstelleKategorie(
            @Valid @RequestBody KategorieDto dto
    ) {
        Kategorie k = svc.create(mapper.dtoZuEntity(dto));
        return mapper.entityZuDto(k);
    }

    @GetMapping
    public List<KategorieDto> alleKategorien() {
        return svc.list().stream()
                .map(mapper::entityZuDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public KategorieDto holeKategorie(@PathVariable Long id) {
        return mapper.entityZuDto(svc.getById(id));
    }

    @PutMapping("/{id}")
    public KategorieDto aktualisiereKategorie(
            @PathVariable Long id,
            @Valid @RequestBody KategorieDto dto
    ) {
        Kategorie updated = svc.update(id, mapper.dtoZuEntity(dto));
        return mapper.entityZuDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loescheKategorie(@PathVariable Long id) {
        svc.delete(id);
    }
}
