// src/test/java/com/example/zeitplaner/service/AufgabeServiceTest.java
package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Aufgabe;
import com.example.zeitplaner.domain.repository.AufgabeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AufgabeServiceTest {
    @Mock AufgabeRepository repo;
    @InjectMocks AufgabeService svc;

    @Test
    void legeAufgabeAn_and_getSortiert() {
        var a = new Aufgabe(null,"T",LocalDateTime.now(),null,null);
        when(repo.save(a)).thenReturn(a);
        when(repo.findAllByOrderByPrioritaetDescDeadlineAsc())
                .thenReturn(List.of(a));

        var saved = svc.legeAufgabeAn(a);
        assertThat(saved).isSameAs(a);

        var sorted = svc.getAufgabenSortiert();
        assertThat(sorted).containsExactly(a);
    }

    @Test
    void updateAufgabe_whenNotFound_throwsNotFound() {
        long id = 42L;
        Aufgabe a = new Aufgabe();
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> svc.updateAufgabe(id, a));
    }

    @Test
    void deleteAufgabe_whenNotFound_throwsNotFound() {
        long id = 43L;
        when(repo.existsById(id)).thenReturn(false);
        assertThrows(ResponseStatusException.class,
                () -> svc.deleteAufgabe(id));
    }

}
