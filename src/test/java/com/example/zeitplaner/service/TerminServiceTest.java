// src/test/java/com/example/zeitplaner/service/TerminServiceTest.java
package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.domain.repository.TerminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TerminServiceTest {

    @Mock
    private TerminRepository repo;

    @Mock
    private WiederholungsRegelService wiederholungsRegelService;

    @Mock
    private KollisionsService kollisionsService;

    @InjectMocks
    private TerminService service;

    private Termin basisTermin;

    @BeforeEach
    void setUp() {
         basisTermin = Termin.builder()
                .start(LocalDateTime.of(2025, 6, 1, 9, 0))
                .ende(LocalDateTime.of(2025, 6, 1, 10, 0))
                .titel("Test")
                .kategorie(null)
                .wiederholungsRegel(null)
                .build();
    }

    @Test
    void legeTerminAn_speichertWennKeineKollision() {
        when(wiederholungsRegelService.expandRecurrence(basisTermin))
                .thenReturn(List.of(basisTermin));
        when(kollisionsService.hatKollision(basisTermin)).thenReturn(false);
        when(repo.save(basisTermin)).thenReturn(basisTermin);

        var result = service.legeTerminAn(basisTermin);

        assertEquals(1, result.size());
        assertSame(basisTermin, result.get(0));
        verify(repo).save(basisTermin);
    }

    @Test
    void legeTerminAn_wirftBeiKollision() {
        when(wiederholungsRegelService.expandRecurrence(basisTermin))
                .thenReturn(List.of(basisTermin));
        when(kollisionsService.hatKollision(basisTermin)).thenReturn(true);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> service.legeTerminAn(basisTermin)
        );
        assertTrue(ex.getMessage().contains("Kollision"));
        verify(repo, never()).save(any());
    }

    @Test
    void alleTermine_gibtRepoErgebnisZurueck() {
        when(repo.findAll()).thenReturn(List.of());
        var alle = service.alleTermine();
        assertTrue(alle.isEmpty());
        verify(repo).findAll();
    }

    @Test
    void aktualisiereTermin_whenNichtGefunden_throwsNotFound() {
        long fakeId = 999L;
        Termin update = new Termin();
        update.setStart(basisTermin.getStart());
        update.setEnde(basisTermin.getEnde());
        update.setTitel("X");

        when(repo.findById(fakeId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> service.updateTermin(fakeId, update)
        );
        assertEquals("404 NOT_FOUND \"Termin nicht gefunden\"", ex.getMessage());
        verify(repo).findById(fakeId);
    }
}
