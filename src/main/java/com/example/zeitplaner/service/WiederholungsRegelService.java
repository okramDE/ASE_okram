package com.example.zeitplaner.service;


import com.example.zeitplaner.domain.model.WiederholungsRegel;
import com.example.zeitplaner.domain.model.Termin;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WiederholungsRegelService {


    public List<Termin> expandRecurrence(Termin base) {
        if (base.getWiederholungsRegel() == null || base.getWiederholungsRegel().isBlank()) {
            return List.of(base);
        }
        WiederholungsRegel rule = WiederholungsRegel.parse(base.getWiederholungsRegel());
        List<Termin> all = new ArrayList<>();
        all.add(base);

        LocalDateTime start = base.getStart();
        LocalDateTime end   = base.getEnde();
        int generated = 1;
        while (rule.getCount() == null || generated < rule.getCount()) {
            switch (rule.getFrequency()) {
                case DAILY   -> { start = start.plusDays(rule.getInterval());   end = end.plusDays(rule.getInterval()); }
                case WEEKLY  -> { start = start.plusWeeks(rule.getInterval());  end = end.plusWeeks(rule.getInterval()); }
                case MONTHLY -> { start = start.plusMonths(rule.getInterval()); end = end.plusMonths(rule.getInterval()); }
                case YEARLY  -> { start = start.plusYears(rule.getInterval());  end = end.plusYears(rule.getInterval()); }
            }
            Termin next = Termin.builder()
                    .start(start)
                    .ende(end)
                    .titel(base.getTitel())
                    .kategorie(base.getKategorie())
                    .wiederholungsRegel(null)  // nur auf dem Basis-Termin
                    .build();
            all.add(next);
            generated++;
            if (rule.getCount() == null) break; // ohne COUNT nur 1 Termin
        }
        return all;
    }
}
