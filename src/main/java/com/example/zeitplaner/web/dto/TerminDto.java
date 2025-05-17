package com.example.zeitplaner.web.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TerminDto {

    // im Response befüllt, im Request nur auf PUT
    private Long id;

    @NotNull
    @Future(message = "Beginn muss in der Zukunft liegen")
    private LocalDateTime beginn;

    @NotNull
    @Future(message = "Ende muss in der Zukunft liegen")
    private LocalDateTime ende;

    @NotBlank
    @Size(max = 100)
    private String titel;

    @NotNull(message = "Kategorie-ID ist erforderlich")
    private Long kategorieId;

    // Optional: z.B. "FREQ=DAILY;COUNT=5"
    private String wiederholungsRegel;
}
