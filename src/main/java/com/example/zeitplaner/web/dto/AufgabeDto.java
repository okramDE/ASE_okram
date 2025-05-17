package com.example.zeitplaner.web.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AufgabeDto {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String titel;

    @NotNull
    @FutureOrPresent(message = "Deadline darf nicht in der Vergangenheit liegen")
    private LocalDateTime deadline;

    @NotNull(message = "Priorität ist erforderlich")
    private String prioritaet; // HIGH, MEDIUM, LOW

    @NotNull(message = "Kategorie-ID ist erforderlich")
    private Long kategorieId;
}
