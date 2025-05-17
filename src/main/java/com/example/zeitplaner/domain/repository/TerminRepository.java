package com.example.zeitplaner.domain.repository;


import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.domain.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TerminRepository extends JpaRepository<Termin, Long> {

    // liefert alle Termine, die zeitlich mit dem neuen Termin kollidieren:
    List<Termin> findByStartBeforeAndEndeAfter(LocalDateTime ende,
                                               LocalDateTime start);

    // Suche nach Kategorie
    List<Termin> findByKategorie(Kategorie kategorie);
    List<Termin> findByKategorie_Id(Long kategorieId);
    List<Termin> findByKategorie_Name(String name);

    // Suche nach Terminen in einem Datumsbereich
    List<Termin> findByStartBetween(LocalDateTime from, LocalDateTime to);

    // Existenzprüfung
    boolean existsByStartBeforeAndEndeAfter(LocalDateTime ende, LocalDateTime start);

}