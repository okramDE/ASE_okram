package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.domain.model.WiederholungsRegel;
import com.example.zeitplaner.domain.strategy.DailyStrategie;
import com.example.zeitplaner.domain.strategy.WeeklyStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WiederholungsRegelServiceTest {

    private WiederholungsRegelService svc;

    @BeforeEach
    void setUp() {
        svc = new WiederholungsRegelService(List.of(
                new DailyStrategie(),
                new WeeklyStrategie(),
                // Monthly, Yearly falls importiert...
                new com.example.zeitplaner.domain.strategy.MonthlyStrategie(),
                new com.example.zeitplaner.domain.strategy.YearlyStrategie()
        ));
    }

    @Test
    void expandRecurrence_noRule_returnsBasis() {
        Termin t = Termin.builder()
                .start(LocalDateTime.now())
                .ende(LocalDateTime.now().plusHours(1))
                .build();
        List<Termin> res = svc.expandRecurrence(t);
        assertThat(res).containsExactly(t);
    }

    @Test
    void expandRecurrence_withDailyRule_appliesStrategy() {
        Termin t = Termin.builder()
                .start(LocalDateTime.of(2025,1,1,9,0))
                .ende(LocalDateTime.of(2025,1,1,10,0))
                .wiederholungsRegel(WiederholungsRegel.parse("FREQ=DAILY;COUNT=2"))
                .build();
        List<Termin> res = svc.expandRecurrence(t);
        // basis + 2 Kopien
        assertThat(res).hasSize(3);
    }
}
