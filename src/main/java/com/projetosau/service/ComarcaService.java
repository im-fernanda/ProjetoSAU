package com.projetosau.service;

import com.projetosau.domain.Comarca;
import com.projetosau.repository.ComarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComarcaService {
    private final ComarcaRepository repository;

    @Autowired
    public ComarcaService(ComarcaRepository repository) {
        this.repository = repository;
    }

    public Comarca create(Comarca comarca) {
        // Verifica se a comarca já existe
        if (repository.existsByNome(comarca.getNome())) {
            throw new IllegalArgumentException("A comarca já existe.");
        }
        return repository.save(comarca);
    }

    public List<Comarca> findAll() {
        return repository.findAll();
    }

    public Optional<Comarca> findById(String id) {
        return repository.findById(id);
    }

    public List<Comarca> findByRegionalId(Long regionalId) {
        return repository.findByRegionalId(regionalId);
    }
}
