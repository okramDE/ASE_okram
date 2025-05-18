// src/main/java/com/example/zeitplaner/web/controller/TerminController.java
package com.example.zeitplaner.web.controller;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.domain.model.WiederholungsRegel;
import com.example.zeitplaner.service.KategorieService;
import com.example.zeitplaner.service.TerminService;
import com.example.zeitplaner.web.dto.TerminCreateDto;
import com.example.zeitplaner.web.dto.TerminDto;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/termine")
public class TerminController {

    private final TerminService terminService;
    private final KategorieService kategorieService;

    public TerminController(TerminService terminService,
                            KategorieService kategorieService) {
        this.terminService    = terminService;
        this.kategorieService = kategorieService;
    }
//    POST http://localhost:8080/api/termine
//    Content-Type: application/json
//
//    {
//        "start": "2025-12-02T10:15:30",
//            "ende": "2025-12-02T10:16:30",
//            "titel": "test1",
//            "kategorieId": 1,
//            "wiederholungsRegel": "FREQ=WEEKLY;INTERVAL=1;COUNT=4"
//    }
    @PostMapping
    public ResponseEntity<List<TerminDto>> erstelleTermin(
            @Valid @RequestBody TerminCreateDto dto
    ) {
        Kategorie kat = kategorieService.getById(dto.getKategorieId());
        Termin basis = new Termin();
        basis.setStart(dto.getStart());
        basis.setEnde(dto.getEnde());
        basis.setTitel(dto.getTitel());
        basis.setKategorie(kat);
        basis.setWiederholungsRegel(
                dto.getWiederholungsRegel() != null
                        ? WiederholungsRegel.parse(dto.getWiederholungsRegel())
                        : null
        );

        List<Termin> gespeicherte = terminService.legeTerminAn(basis);

        List<TerminDto> out = gespeicherte.stream().map(t -> {
            TerminDto o = new TerminDto();
            o.setId(t.getId());
            o.setStart(t.getStart());
            o.setEnde(t.getEnde());
            o.setTitel(t.getTitel());
            o.setKategorieId(t.getKategorie().getId());
            o.setWiederholungsRegel(
                    t.getWiederholungsRegel() == null
                            ? null
                            : t.getWiederholungsRegel().toRRuleString()
            );
            return o;
        }).collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(out);
    }

    //GET http://localhost:8080/api/termine
    @GetMapping
    public List<TerminDto> alleTermine() {
        return terminService.alleTermine().stream().map(t -> {
            TerminDto o = new TerminDto();
            o.setId(t.getId());
            o.setStart(t.getStart());
            o.setEnde(t.getEnde());
            o.setTitel(t.getTitel());
            o.setKategorieId(t.getKategorie().getId());
            o.setWiederholungsRegel(
                    t.getWiederholungsRegel() != null
                            ? t.getWiederholungsRegel().toRRuleString()
                            : null
            );
            return o;
        }).collect(Collectors.toList());
    }
//@id = 2
//GET http://localhost:8080/api/termine/{{id}}
    @GetMapping("/{id}")
    public TerminDto holeTermin(@PathVariable Long id) {
        Termin t = terminService.getById(id);
        TerminDto o = new TerminDto();
        o.setId(t.getId());
        o.setStart(t.getStart());
        o.setEnde(t.getEnde());
        o.setTitel(t.getTitel());
        o.setKategorieId(t.getKategorie().getId());
        o.setWiederholungsRegel(
                t.getWiederholungsRegel() != null
                        ? t.getWiederholungsRegel().toRRuleString()
                        : null
        );
        return o;
    }

//    @id = 2
//    PUT http://localhost:8080/api/termine/{{id}}
//    Content-Type: application/json
//
//    {
//        "start": "2025-06-10T09:00:00",
//            "ende": "2025-06-10T10:00:00",
//            "titel": "testkolli",
//            "kategorieId": 2,
//            "wiederholungsRegel": ""
//    }
    @PutMapping("/{id}")
    public TerminDto aktualisiereTermin(
            @PathVariable Long id,
            @Valid @RequestBody TerminDto dto
    ) {
        Kategorie kat = kategorieService.getById(dto.getKategorieId());
        Termin neu = new Termin();
        neu.setStart(dto.getStart());
        neu.setEnde(dto.getEnde());
        neu.setTitel(dto.getTitel());
        neu.setKategorie(kat);


        Termin updated = terminService.updateTermin(id, neu);
        TerminDto o = new TerminDto();
        o.setId(updated.getId());
        o.setStart(updated.getStart());
        o.setEnde(updated.getEnde());
        o.setTitel(updated.getTitel());
        o.setKategorieId(updated.getKategorie().getId());
        o.setWiederholungsRegel(
                updated.getWiederholungsRegel() == null
                        ? null
                        : updated.getWiederholungsRegel().toRRuleString()
        );
        return o;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loescheTermin(@PathVariable Long id) {
        terminService.deleteTermin(id);
    }

    //@kategorieId = 2
    //GET http://localhost:8080/api/termine/kategorie/{{kategorieId}}
    @GetMapping("/kategorie/{kategorieId}")
    public List<TerminDto> termineNachKategorie(@PathVariable Long kategorieId) {
        return terminService.sucheNachKategorieId(kategorieId).stream().map(t -> {
            TerminDto o = new TerminDto();
            o.setId(t.getId());
            o.setStart(t.getStart());
            o.setEnde(t.getEnde());
            o.setTitel(t.getTitel());
            o.setKategorieId(t.getKategorie().getId());
            o.setWiederholungsRegel(
                                  t.getWiederholungsRegel() != null
                                           ? t.getWiederholungsRegel().toRRuleString()
                                            : null
                                        );
            return o;
        }).collect(Collectors.toList());
    }
//GET http://localhost:8080/api/termine/zeitraum?von=2025-06-01T00:00:00&bis=2025-06-20T23:59:59
    @GetMapping("/zeitraum")
    public List<TerminDto> termineZeitraum(
            @RequestParam("von") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime von,
            @RequestParam("bis") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bis
    ) {
        List<Termin> ergebnis = terminService.sucheNachZeitraum( von, bis);
        return ergebnis.stream().map(t -> {
            TerminDto o = new TerminDto();
            o.setId(t.getId());
            o.setStart(t.getStart());
            o.setEnde(t.getEnde());
            o.setTitel(t.getTitel());
            o.setKategorieId(t.getKategorie().getId());
            o.setWiederholungsRegel(
                    t.getWiederholungsRegel() != null
                            ? t.getWiederholungsRegel().toRRuleString()
                            : null
            );
            return o;
        }).collect(Collectors.toList());
    }

}
