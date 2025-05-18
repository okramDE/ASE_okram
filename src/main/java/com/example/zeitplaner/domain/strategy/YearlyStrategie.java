package com.example.zeitplaner.domain.strategy;

import com.example.zeitplaner.domain.model.Termin;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class YearlyStrategie implements WiederholungsStrategie {

    @Override
    public String getFrequency() {
        return "YEARLY";
    }

    @Override
    public List<Termin> generiereWiederholungen(Termin basis, int interval, Integer count) {
        List<Termin> alle = new ArrayList<>();
        LocalDateTime start = basis.getStart();
        LocalDateTime end   = basis.getEnde();
        int generated = 0;

        while (count == null || generated < count) {
            start = start.plusYears(interval);
            end   = end.plusYears(interval);
            Termin kopie = Termin.builder()
                    .start(start)
                    .ende(end)
                    .titel(basis.getTitel())
                    .kategorie(basis.getKategorie())
                    .wiederholungsRegel(null)
                    .build();
            alle.add(kopie);
            generated++;
            if (count == null) break;
        }
        return alle;
    }
}
