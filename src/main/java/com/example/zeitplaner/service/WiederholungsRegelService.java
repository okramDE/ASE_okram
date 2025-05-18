package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.domain.model.WiederholungsRegel;
import com.example.zeitplaner.domain.strategy.WiederholungsStrategie;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WiederholungsRegelService {


    private final Map<WiederholungsRegel.Frequency, WiederholungsStrategie> strategien;

    public WiederholungsRegelService(List<WiederholungsStrategie> strategien) {

                this.strategien = new EnumMap<>(WiederholungsRegel.Frequency.class);
                for (WiederholungsStrategie s : strategien) {
                        // wir gehen davon aus, dass getFrequency() den gleichen Namen wie das Enum liefert
                                WiederholungsRegel.Frequency freq =
                                    WiederholungsRegel.Frequency.valueOf(s.getFrequency());
                        this.strategien.put(freq, s);
                    }
    }

    public List<Termin> expandRecurrence(Termin basis) {

                // direkt das VO auslesen
                        WiederholungsRegel wr = basis.getWiederholungsRegel();
                if (wr == null) {
                        return List.of(basis);
                   }


                WiederholungsStrategie strat = strategien.get(wr.getFrequency());
        if (strat == null) {
            throw new IllegalArgumentException("Unbekannte Frequenz: " + wr.getFrequency());
        }
        // Basis-Termin selbst hinzufügen
        List<Termin> result = new ArrayList<>();
        result.add(basis);
        // und dann die Wiederholungen via Strategy
        result.addAll(strat.generiereWiederholungen(
                basis, wr.getInterval(), wr.getCount()));
        return result;
    }
}