package com.example.zeitplaner.domain.strategy;

import com.example.zeitplaner.domain.model.Termin;
import java.util.List;

public interface WiederholungsStrategie {
    /**
     * Liefert die erzeugten Termine für die gegebene Basis-Instanz,
     * entsprechend der konkreten Frequenz.
     */
    List<Termin> generiereWiederholungen(Termin basis, int interval, Integer count);

    /**
     * Welche FREQ-Konstante (z.B. "DAILY") unterstützt diese Strategie?
     */
    String getFrequency();
}