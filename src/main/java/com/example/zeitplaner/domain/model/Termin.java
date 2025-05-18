package com.example.zeitplaner.domain.model;



import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Setter @Getter
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime start;
    private LocalDateTime ende;
    private String titel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "kategorie_id")
    private Kategorie kategorie;

    @Convert(converter = WiederholungsRegelConverter.class)
    private WiederholungsRegel wiederholungsRegel;


}