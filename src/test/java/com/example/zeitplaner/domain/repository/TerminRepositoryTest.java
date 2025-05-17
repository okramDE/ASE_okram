// src/test/java/com/example/zeitplaner/domain/repository/TerminRepositoryTest.java
package com.example.zeitplaner.domain.repository;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.domain.model.Termin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TerminRepositoryTest {

    @Autowired
    private TerminRepository terminRepo;
    @Autowired
    private KategorieRepository kategorieRepo;

    @Test
    void findByBeginnBetween_returnsMatching() {
        // 1. lege eine Kategorie an und speichere sie
        Kategorie kat = new Kategorie();
        kat.setName("Arbeit");
        kat = kategorieRepo.save(kat);

        // 2. erstelle zwei Termine mit genau dieser Kategorie
        Termin a = new Termin();
        a.setStart(LocalDateTime.of(2025, 6, 1, 9, 0));
        a.setEnde(LocalDateTime.of(2025, 6, 1, 10, 0));
        a.setTitel("A");
        a.setKategorie(kat);
        terminRepo.save(a);

        Termin b = new Termin();
        b.setStart(LocalDateTime.of(2025, 7, 1, 9, 0));
        b.setEnde(LocalDateTime.of(2025, 7, 1, 10, 0));
        b.setTitel("B");
        b.setKategorie(kat);
        terminRepo.save(b);

        // 3. frage nur den Juni ab
        List<Termin> result = terminRepo.findByStartBetween(
                LocalDateTime.of(2025, 6, 1, 0, 0),
                LocalDateTime.of(2025, 6, 30, 23, 59)
        );

        assertThat(result)
                .hasSize(1)
                .first()
                .returns("A", Termin::getTitel);
    }
}
