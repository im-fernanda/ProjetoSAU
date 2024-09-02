package com.projetosau.service;

import com.projetosau.domain.Unidade;
import com.projetosau.repository.UnidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeService {
    private final UnidadeRepository repository;

    public UnidadeService(UnidadeRepository repository) {
        this.repository = repository;
    }

    public Optional<Unidade> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Unidade update(Unidade unidade) {
        return repository.saveAndFlush(unidade);
    }

    public Unidade create(Unidade unidade) {
        if (repository.findById(unidade.getId()).isPresent()) {
            throw new IllegalArgumentException("A unidade já existe.");
        }
        return repository.save(unidade);
    }

    public List<Unidade> findAll() {
        return repository.findAll();
    }
}

