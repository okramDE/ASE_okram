package com.example.zeitplaner.domain.model;

import lombok.*;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WiederholungsRegel {

    public enum Frequency { DAILY, WEEKLY, MONTHLY, YEARLY }

    /** Art der Wiederholung, z. B. DAILY */
    @NotNull
    private Frequency frequency;

    /** Abstand (INTERVAL) – mindestens 1 */
    @Min(1)
    private int interval = 1;

    /** Anzahl der Wiederholungen (COUNT), optional */
    @Min(1)
    private Integer count;

    /** Enddatum (UNTIL), optional, im Format YYYYMMDD'T'HHMMSS'Z' */
    private LocalDateTime until;

    /** Wochentage (BYDAY), optional */
    @Builder.Default
    private List<String> byDay = new ArrayList<>();

    /** Monatstage (BYMONTHDAY), optional */
    @Builder.Default
    private List<Integer> byMonthDay = new ArrayList<>();

    private static final DateTimeFormatter UNTIL_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

    /**
     * Parst einen RFC-5545-konformen RRULE-String.
     * Beispiel: "FREQ=DAILY;COUNT=10;UNTIL=20250601T000000Z;BYDAY=MO,WE;BYMONTHDAY=1,15"
     *
     * @throws IllegalArgumentException bei null/leer oder unbekanntem Schlüssel
     */
    public static WiederholungsRegel parse(String rule) {
        if (rule == null || rule.isBlank()) {
            throw new IllegalArgumentException("RRULE darf nicht null oder leer sein");
        }
        WiederholungsRegel rr = new WiederholungsRegel();
        for (String part : rule.split(";")) {
            String[] kv = part.split("=", 2);
            if (kv.length != 2) {
                throw new IllegalArgumentException("Ungültiges RRULE-Teil: " + part);
            }
            String key = kv[0], val = kv[1];
            try {
                switch (key) {
                    case "FREQ"       -> rr.frequency  = Frequency.valueOf(val);
                    case "INTERVAL"   -> rr.interval   = Integer.parseInt(val);
                    case "COUNT"      -> rr.count      = Integer.valueOf(val);
                    case "UNTIL"      -> rr.until      = LocalDateTime.parse(val, UNTIL_FORMAT);
                    case "BYDAY"      -> rr.byDay      = List.of(val.split(","));
                    case "BYMONTHDAY" -> rr.byMonthDay = Arrays.stream(val.split(","))
                            .map(Integer::valueOf)
                            .collect(Collectors.toList());
                    default -> throw new IllegalArgumentException("Unbekannter RRULE-Schlüssel: " + key);
                }
            } catch (DateTimeParseException | IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Fehler beim Parsen von '" + key + "=" + val + "'", e);
            }
        }
        if (rr.frequency == null) {
            throw new IllegalArgumentException("RRULE muss eine FREQ-Angabe enthalten");
        }
        return rr;
    }

    /**
     * Wandelt dieses VO zurück in einen RRULE-String.
     */
    public String toRRuleString() {
        List<String> parts = new ArrayList<>();
        parts.add("FREQ=" + frequency.name());
        if (interval > 1)        parts.add("INTERVAL=" + interval);
        if (count != null)       parts.add("COUNT=" + count);
        if (until != null)       parts.add("UNTIL=" + UNTIL_FORMAT.format(until));
        if (!byDay.isEmpty())    parts.add("BYDAY=" + String.join(",", byDay));
        if (!byMonthDay.isEmpty()){
            String md = byMonthDay.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
            parts.add("BYMONTHDAY=" + md);
        }
        return String.join(";", parts);
    }
}
