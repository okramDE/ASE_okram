package com.example.zeitplaner.service;

import com.example.zeitplaner.domain.model.Termin;
import com.example.zeitplaner.domain.repository.TerminRepository;
import org.springframework.stereotype.Service;

@Service
public class KollisionsService {
    private final TerminRepository repo;
    public KollisionsService(TerminRepository repo) { this.repo = repo; }
    public boolean hatKollision(Termin t) {
        return repo.existsByStartBeforeAndEndeAfter(t.getEnde(), t.getStart());
    }
}

