package com.projetosau.service;

import com.projetosau.domain.Comarca;
import com.projetosau.domain.Regional;
import com.projetosau.domain.Unidade;
import com.projetosau.repository.RegionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class RegionalService {

    private final RegionalRepository repository;

    @Autowired
    public RegionalService(RegionalRepository repository) {
        this.repository = repository;
    }

    public List<Regional> findAll() {
        return repository.findAll();
    }

    public Optional<Regional> findById(Long id) {
        return repository.findById(id);
    }

    public Regional create(Regional regional) {
        if (repository.existsByNome(regional.getNome())) {
            throw new IllegalArgumentException("A regional já existe.");
        }
        return repository.save(regional);
    }


}
