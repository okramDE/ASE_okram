// src/test/java/com/example/zeitplaner/service/KategorieServiceTest.java
package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.domain.repository.KategorieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KategorieServiceTest {
    @Mock KategorieRepository repo;
    @InjectMocks KategorieService svc;

    @Test
    void create_and_list() {
        var k = new Kategorie(null,"X");
        when(repo.existsByName("X")).thenReturn(false);
        when(repo.save(k)).thenReturn(k);
        when(repo.findAll()).thenReturn(List.of(k));

        var created = svc.create(k);
        assertThat(created).isSameAs(k);
        assertThat(svc.list()).containsExactly(k);
    }

    @Test
    void create_conflict() {
        var k = new Kategorie(null,"Y");
        when(repo.existsByName("Y")).thenReturn(true);
        assertThatThrownBy(() -> svc.create(k))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("existiert bereits");
    }
}
