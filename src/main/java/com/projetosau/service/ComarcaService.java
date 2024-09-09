package com.projetosau.service;

import com.projetosau.domain.Comarca;
import com.projetosau.domain.Regional;
import com.projetosau.repository.ComarcaRepository;
import com.projetosau.repository.RegionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComarcaService {
    private final ComarcaRepository repository;
    private final RegionalRepository regionalRepository;

    @Autowired
    public ComarcaService(ComarcaRepository repository, RegionalRepository regionalRepository) {
        this.repository = repository;
        this.regionalRepository = regionalRepository;
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

    public Optional<Comarca> findById(Long id) {
        return repository.findById(id);
    }

    public List<Comarca> findByRegional(Long regionalId) {
        Regional regional = regionalRepository.findById(regionalId)
                .orElseThrow(() -> new IllegalArgumentException("Regional não encontrado"));
        return repository.findByRegional(regional);
    }
}
