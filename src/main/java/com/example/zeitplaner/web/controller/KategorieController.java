// src/main/java/com/example/zeitplaner/web/controller/KategorieController.java
package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.service.KategorieService;
import com.example.zeitplaner.web.dto.KategorieCreateDto;
import com.example.zeitplaner.web.dto.KategorieDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

//    GET http://localhost:8080/api/kategorien/{{id}}
//    Content-Type: application/json
//
//    {
//        "id": 0,
//            "name": ""
//    }
    @PostMapping
    public ResponseEntity<KategorieDto> erstelleKategorie(
            @Valid @RequestBody KategorieCreateDto dto
    ) {
        Kategorie k = new Kategorie();
        k.setName(dto.getName());
        Kategorie saved = kategorieService.create(k);

        KategorieDto out = new KategorieDto();
        out.setId(saved.getId());    // generierte id
        out.setName(saved.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(out);
    }

//    GET http://localhost:8080/api/kategorien
//    Content-Type: application/json
//
//    {
//        "name": ""
//    }
    @GetMapping
    public List<KategorieDto> alleKategorien() {
        return kategorieService.list().stream().map(k -> {
            KategorieDto o = new KategorieDto();
            o.setId(k.getId());
            o.setName(k.getName());
            return o;
        }).collect(Collectors.toList());
    }

//    @id = 1
//    GET http://localhost:8080/api/kategorien/{{id}}
//    Content-Type: application/json

    @GetMapping("/suche")
    public KategorieDto sucheKategorieNachName(@RequestParam("name") String name) {
        Kategorie k = kategorieService.getByName(name);
        KategorieDto o = new KategorieDto();
        o.setId(k.getId());
        o.setName(k.getName());
        return o;
    }

    @GetMapping("/{id}")
    public KategorieDto holeKategorie(@PathVariable Long id) {
        Kategorie k = kategorieService.getById(id);
        KategorieDto o = new KategorieDto();
        o.setId(k.getId());
        o.setName(k.getName());
        return o;
    }

//    @id = 2
//    PUT http://localhost:8080/api/kategorien/{{id}}
//    Content-Type: application/json
//
//    {
//        "id": 2,
//            "name": "testupdate"
//    }

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

//    @id = 3
//    DELETE http://localhost:8080/api/kategorien/{{id}}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loescheKategorie(@PathVariable Long id) {
        kategorieService.delete(id);
    }
}
