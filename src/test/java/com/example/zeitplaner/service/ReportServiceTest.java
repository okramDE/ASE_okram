// src/test/java/com/example/zeitplaner/service/ReportServiceTest.java
package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Kategorie;
import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.domain.repository.TerminRepository;
import com.example.zeitplaner.web.dto.TimeUsageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock TerminRepository repo;
    @InjectMocks ReportService svc;

    @Test
    void zeitnutzung_summiertProKategorie() {
        var kat = new Kategorie(1L,"K");
        var t1 = new Termin(1L,
                LocalDateTime.of(2025,5,1,9,0),
                LocalDateTime.of(2025,5,1,10,0),
                "T", kat, null);
        var t2 = new Termin(2L,
                LocalDateTime.of(2025,5,2,9,0),
                LocalDateTime.of(2025,5,2,11,0),
                "T2", kat, null);
        when(repo.findAll()).thenReturn(List.of(t1,t2));

        var res = svc.zeitnutzung(LocalDate.of(2025,5,1).atStartOfDay(),
                LocalDate.of(2025,5,31).atStartOfDay());
        assertThat(res).hasSize(1)
                .first()
                .matches(dto ->
                        dto.getKategorieId().equals(1L) &&
                                dto.getMinuten() == Duration.between(
                                        t1.getStart(), t1.getEnde()).toMinutes() +
                                        Duration.between(t2.getStart(), t2.getEnde()).toMinutes());
    }
}
