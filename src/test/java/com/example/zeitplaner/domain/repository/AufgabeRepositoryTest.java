// src/test/java/com/example/zeitplaner/domain/repository/AufgabeRepositoryTest.java
package com.example.zeitplaner.domain.repository;

import com.example.zeitplaner.domain.model.Aufgabe;
import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.domain.model.Prioritaet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AufgabeRepositoryTest {

    @Autowired
    private AufgabeRepository aufgabeRepo;

    @Autowired
    private KategorieRepository kategorieRepo;

    @Test
    void findByDeadlineBetween_returnsMatching() {
        // 1. eine Kategorie anlegen und speichern
        Kategorie kat = new Kategorie();
        kat.setName("TestKat");
        kat = kategorieRepo.save(kat);

        // 2. zwei Aufgaben mit dieser Kategorie anlegen
        var now = LocalDateTime.now().plusDays(1);
        Aufgabe a = new Aufgabe();
        a.setTitel("A");
        a.setDeadline(now);
        a.setPrioritaet(Prioritaet.LOW);
        a.setKategorie(kat);
        aufgabeRepo.save(a);

        Aufgabe b = new Aufgabe();
        b.setTitel("B");
        b.setDeadline(now.plusDays(10));
        b.setPrioritaet(Prioritaet.HIGH);
        b.setKategorie(kat);
        aufgabeRepo.save(b);

        // 3. Abfrage testen
        var zwischen = aufgabeRepo.findByDeadlineBetween(
                now.minusHours(1), now.plusHours(1));
        assertThat(zwischen)
                .hasSize(1)
                .first()
                .returns("A", Aufgabe::getTitel);
    }

    @Test
    void findAllByOrderByPrioritaetDescDeadlineAsc_sortsCorrectly() {
        // 1. Kategorie anlegen
        Kategorie kat = kategorieRepo.save(new Kategorie(null, "K"));

        // 2. Aufgaben erstellen
        Aufgabe a = new Aufgabe();
        a.setTitel("A");
        a.setDeadline(LocalDateTime.now().plusDays(2));
        a.setPrioritaet(Prioritaet.MEDIUM);
        a.setKategorie(kat);
        aufgabeRepo.save(a);

        Aufgabe b = new Aufgabe();
        b.setTitel("B");
        b.setDeadline(LocalDateTime.now().plusDays(1));
        b.setPrioritaet(Prioritaet.HIGH);
        b.setKategorie(kat);
        aufgabeRepo.save(b);

        // 3. Sortiert abfragen
        var sorted = aufgabeRepo.findAllByOrderByPrioritaetDescDeadlineAsc();
        assertThat(sorted.get(0).getTitel()).isEqualTo("B");
    }
}
