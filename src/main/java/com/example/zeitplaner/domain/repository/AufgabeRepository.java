package com.example.zeitplaner.domain.repository;

import com.example.zeitplaner.domain.model.Aufgabe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AufgabeRepository extends JpaRepository<Aufgabe, Long> {


    // liefert alle Aufgaben, sortiert nach Priorität absteigend, Deadline aufsteigend
    List<Aufgabe> findAllByOrderByPrioritaetDescDeadlineAsc();

    // Suche nach Deadline im Bereich
    List<Aufgabe> findByDeadlineBetween(LocalDateTime from, LocalDateTime to);

}