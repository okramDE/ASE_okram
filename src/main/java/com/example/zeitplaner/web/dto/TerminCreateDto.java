package com.example.zeitplaner.web.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TerminCreateDto {

    @NotNull @Future
    private LocalDateTime start;

    @NotNull @Future
    private LocalDateTime ende;

    @NotBlank @Size(max = 100)
    private String titel;

    @NotNull
    private Long kategorieId;

    // optional
    private String wiederholungsRegel;
}
