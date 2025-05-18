package com.example.zeitplaner.domain.strategy;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.domain.strategy.DailyStrategie;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DailyStrategieTest {

    private final DailyStrategie strat = new DailyStrategie();

    @Test
    void generiereWiederholungen_withCount_generatesExactInstances() {
        Termin basis = Termin.builder()
                .start(LocalDateTime.of(2025,1,1,9,0))
                .ende(LocalDateTime.of(2025,1,1,10,0))
                .build();

        List<Termin> result = strat.generiereWiederholungen(basis, 1, 3);
        // erwarten 3 Elemente: 1., 2., 3. Tag
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getStart().toLocalDate()).isEqualTo(basis.getStart().toLocalDate().plusDays(1));
        assertThat(result.get(2).getStart().toLocalDate()).isEqualTo(basis.getStart().toLocalDate().plusDays(3));
    }

    @Test
    void generiereWiederholungen_noCount_generatesSingleInstance() {
        Termin basis = Termin.builder()
                .start(LocalDateTime.of(2025,1,1,9,0))
                .ende(LocalDateTime.of(2025,1,1,10,0))
                .build();

        List<Termin> result = strat.generiereWiederholungen(basis, 2, null);
        // ohne COUNT borde wir genau eine Kopie erzeugen und dann abbrechen
        assertThat(result).hasSize(1)
                .first()
                .matches(t -> t.getStart().isEqual(basis.getStart().plusDays(2)));
    }
}
