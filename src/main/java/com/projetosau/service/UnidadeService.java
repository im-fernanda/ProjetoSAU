package com.projetosau.service;

import com.projetosau.domain.Comarca;
import com.projetosau.domain.Regional;
import com.projetosau.domain.Unidade;
import com.projetosau.repository.ComarcaRepository;
import com.projetosau.repository.UnidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeService {
    private final UnidadeRepository repository;
    private final ComarcaRepository comarcaRepository;

    public UnidadeService(UnidadeRepository repository, ComarcaRepository comarcaRepository) {
        this.repository = repository;
        this.comarcaRepository = comarcaRepository;
    }

    public Optional<Unidade> findById(Long id) {
        return repository.findById(id);
    }


    public Unidade create(Unidade unidade) {
        if (repository.existsByNome(unidade.getNome())) {
            throw new IllegalArgumentException("A unidade já existe.");
        }
        // Verifica se a comarca associada tem um ID válido
        if (unidade.getComarca() != null && unidade.getComarca().getId() == null) {
            throw new IllegalArgumentException("A comarca associada deve ter um ID válido.");
        }

        return repository.save(unidade);
    }

    public void delete(Long id) {
        Optional<Unidade> unidade = repository.findById(id);
        if (unidade.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Unidade não encontrada com o ID: " + id);
        }
    }

    public Unidade update(Unidade unidade) {
        return repository.saveAndFlush(unidade);
    }

    public List<Unidade> findAll() {
        return repository.findAll();
    }

    public List<Unidade> findByComarca(Long comarcaId) {
        Optional<Comarca> comarca = comarcaRepository.findById(comarcaId);
            if (comarca.isPresent()) {
            return repository.findByComarca(comarca.get());
        } else {
            throw new IllegalArgumentException("Comarca não encontrada.");
        }
    }

}

