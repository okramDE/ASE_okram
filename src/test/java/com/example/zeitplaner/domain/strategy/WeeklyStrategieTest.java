package com.example.zeitplaner.domain.strategy;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.domain.strategy.WeeklyStrategie;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WeeklyStrategieTest {

    private final WeeklyStrategie strat = new WeeklyStrategie();

    @Test
    void generiereWiederholungen_generatesWeekly() {
        Termin basis = Termin.builder()
                .start(LocalDateTime.of(2025,1,1,9,0))
                .ende(LocalDateTime.of(2025,1,1,10,0))
                .build();

        List<Termin> result = strat.generiereWiederholungen(basis, 1, 2);
        assertThat(result).hasSize(2);
        assertThat(result.get(1).getStart().toLocalDate())
                .isEqualTo(basis.getStart().toLocalDate().plusWeeks(2));
    }
}
