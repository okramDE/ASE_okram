package com.example.zeitplaner.web.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KategorieCreateDto {

    @NotBlank @Size(max = 50)
    private String name;
}
