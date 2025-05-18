package com.example.zeitplaner.web.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AufgabeCreateDto {

    @NotBlank @Size(max = 100)
    private String titel;

    @NotNull @FutureOrPresent
    private LocalDateTime deadline;

    @NotNull
    private String prioritaet;  // "HIGH" | "MEDIUM" | "LOW"

    @NotNull
    private Long kategorieId;
}
