package com.example.zeitplaner.domain.model;


import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder @Setter @Getter
public class Aufgabe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titel;
    private LocalDateTime deadline;

    @Enumerated(EnumType.ORDINAL)
    private Prioritaet prioritaet;

    @ManyToOne(optional = false)
    @JoinColumn(name = "kategorie_id")
    private Kategorie kategorie;
}