// src/test/java/com/example/zeitplaner/domain/repository/KategorieRepositoryTest.java
package com.example.zeitplaner.domain.repository;

import com.example.zeitplaner.domain.model.Kategorie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class KategorieRepositoryTest {

    @Autowired
    private KategorieRepository repo;

    @Test
    void existsByName_and_findByName() {
        var k = new Kategorie(null,"TestKat");
        repo.save(k);

        assertThat(repo.existsByName("TestKat")).isTrue();
        assertThat(repo.findByName("TestKat").getName()).isEqualTo("TestKat");
    }
}
