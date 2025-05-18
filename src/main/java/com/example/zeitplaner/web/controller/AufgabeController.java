package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Aufgabe;
import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.domain.model.Prioritaet;
import com.example.zeitplaner.service.AufgabeService;
import com.example.zeitplaner.service.KategorieService;
import com.example.zeitplaner.web.dto.AufgabeCreateDto;
import com.example.zeitplaner.web.dto.AufgabeDto;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aufgaben")
public class AufgabeController {

    private final AufgabeService aufgabeService;
    private final KategorieService kategorieService;

    public AufgabeController(AufgabeService aufgabeService,
                             KategorieService kategorieService) {
        this.aufgabeService   = aufgabeService;
        this.kategorieService = kategorieService;
    }

//    POST http://localhost:8080/api/aufgaben
//    Content-Type: application/json
//
//    {
//        "titel": "test",
//            "deadline": "2025-12-03T10:15:30",
//            "prioritaet": "MEDIUM",
//            "kategorieId": 1
//    }
    @PostMapping
    public ResponseEntity<AufgabeDto> erstelleAufgabe(
            @Valid @RequestBody AufgabeCreateDto dto
    ) {
        Kategorie kat = kategorieService.getById(dto.getKategorieId());

        Aufgabe a = new Aufgabe();
        a.setTitel(dto.getTitel());
        a.setDeadline(dto.getDeadline());
        a.setPrioritaet(Prioritaet.valueOf(dto.getPrioritaet()));
        a.setKategorie(kat);

        Aufgabe saved = aufgabeService.legeAufgabeAn(a);

        AufgabeDto out = new AufgabeDto();
        out.setId(saved.getId());      // hier kommt die generierte id
        out.setTitel(saved.getTitel());
        out.setDeadline(saved.getDeadline());
        out.setPrioritaet(saved.getPrioritaet().name());
        out.setKategorieId(saved.getKategorie().getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(out);
    }

//GET http://localhost:8080/api/aufgaben
    @GetMapping
    public List<AufgabeDto> alleAufgaben() {
        return aufgabeService.getAufgabenSortiert().stream().map(a -> {
            AufgabeDto o = new AufgabeDto();
            o.setId(a.getId());
            o.setTitel(a.getTitel());
            o.setDeadline(a.getDeadline());
            o.setPrioritaet(a.getPrioritaet().name());
            o.setKategorieId(a.getKategorie().getId());
            return o;
        }).collect(Collectors.toList());
    }

    @GetMapping("/suche-kategorie")
    public List<AufgabeDto> sucheNachKategorieName(@RequestParam("name") String name) {
        List<Aufgabe> aufgaben = aufgabeService.sucheNachKategorieName(name);
        return aufgaben.stream().map(a -> {
            AufgabeDto o = new AufgabeDto();
            o.setId(a.getId());
            o.setTitel(a.getTitel());
            o.setDeadline(a.getDeadline());
            o.setPrioritaet(a.getPrioritaet().name());
            o.setKategorieId(a.getKategorie().getId());
            return o;
        }).collect(Collectors.toList());
    }

    //@id = 1
    //GET http://localhost:8080/api/aufgaben/{{id}}
    @GetMapping("/{id}")
    public AufgabeDto holeAufgabe(@PathVariable Long id) {
        Aufgabe a = aufgabeService.getById(id);
        AufgabeDto o = new AufgabeDto();
        o.setId(a.getId());
        o.setTitel(a.getTitel());
        o.setDeadline(a.getDeadline());
        o.setPrioritaet(a.getPrioritaet().name());
        o.setKategorieId(a.getKategorie().getId());
        return o;
    }

//    @id = 2
//    PUT http://localhost:8080/api/aufgaben/{{id}}
//    Content-Type: application/json
//
//    {
//        "id": 2,
//            "titel": "testupdate",
//            "deadline": "2025-12-03T10:15:30",
//            "prioritaet": "HIGH",
//            "kategorieId": 1
//    }
    @PutMapping("/{id}")
    public AufgabeDto aktualisiereAufgabe(
            @PathVariable Long id,
            @Valid @RequestBody AufgabeDto dto
    ) {
        Kategorie kat = kategorieService.getById(dto.getKategorieId());

        Aufgabe neu = new Aufgabe();
        neu.setTitel(dto.getTitel());
        neu.setDeadline(dto.getDeadline());
        neu.setPrioritaet(Prioritaet.valueOf(dto.getPrioritaet()));
        neu.setKategorie(kat);

        Aufgabe updated = aufgabeService.updateAufgabe(id, neu);

        AufgabeDto o = new AufgabeDto();
        o.setId(updated.getId());
        o.setTitel(updated.getTitel());
        o.setDeadline(updated.getDeadline());
        o.setPrioritaet(updated.getPrioritaet().name());
        o.setKategorieId(updated.getKategorie().getId());
        return o;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loescheAufgabe(@PathVariable Long id) {
        aufgabeService.deleteAufgabe(id);
    }

    //GET http://localhost:8080/api/aufgaben/suche?von=2025-12-02T10:15:30&bis=2025-12-08T10:15:30
    @GetMapping("/suche")
    public List<AufgabeDto> sucheNachDeadline(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime von,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime bis
    ) {
        return aufgabeService.sucheNachDeadline(von, bis).stream().map(a -> {
            AufgabeDto o = new AufgabeDto();
            o.setId(a.getId());
            o.setTitel(a.getTitel());
            o.setDeadline(a.getDeadline());
            o.setPrioritaet(a.getPrioritaet().name());
            o.setKategorieId(a.getKategorie().getId());
            return o;
        }).collect(Collectors.toList());
    }
}
