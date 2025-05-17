package com.example.zeitplaner.domain.repository;

import com.example.zeitplaner.domain.model.Kategorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategorieRepository extends JpaRepository<Kategorie, Long> {
    boolean existsByName(String name);
    Kategorie findByName(String name);
}