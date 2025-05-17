package com.example.zeitplaner.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WiederholungsRegel {
    public enum Frequency { DAILY, WEEKLY, MONTHLY, YEARLY }

    private Frequency frequency;
    private int interval = 1;    // alle X Tage/Wochen…
    private Integer count;       // Anzahl der Wiederholungen (optional)
    // TODO: UNTIL-Feld, BYDAY etc. erweitern

    public static WiederholungsRegel parse(String rule) {
        WiederholungsRegel rr = new WiederholungsRegel();
        for (String part : rule.split(";")) {
            String[] kv = part.split("=");
            switch (kv[0]) {
                case "FREQ" -> rr.frequency = Frequency.valueOf(kv[1]);
                case "INTERVAL" -> rr.interval = Integer.parseInt(kv[1]);
                case "COUNT" -> rr.count = Integer.valueOf(kv[1]);
            }
        }
        return rr;
    }
}