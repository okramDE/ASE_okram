package com.example.zeitplaner.domain.repository;

import com.example.zeitplaner.domain.model.Aufgabe;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@RepositoryRestResource
public interface AufgabeRepository extends JpaRepository<Aufgabe, Long> {


    // liefert alle Aufgaben, sortiert nach Priorität absteigend, Deadline aufsteigend
    List<Aufgabe> findAllByOrderByPrioritaetDescDeadlineAsc();

    // Suche nach Deadline im Bereich
    List<Aufgabe> findByDeadlineBetween(LocalDateTime from, LocalDateTime to);

    List<Aufgabe> findByKategorie_Name(String name);
}