package com.projetosau.service;

import org.springframework.stereotype.Service;
import com.projetosau.domain.Equipamento;
import com.projetosau.repository.EquipamentoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {

    private final EquipamentoRepository repository;

    public EquipamentoService(EquipamentoRepository repository) {
        this.repository = repository;
    }

    public Optional<Equipamento> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Equipamento update(Equipamento equipamento) {
        return repository.saveAndFlush(equipamento);
    }

    public Equipamento create(Equipamento equipamento) {
        if (repository.existsByTombo(equipamento.getTombo())) {
            throw new IllegalArgumentException("O tombo já existe. Escolha um tombo diferente.");
        }

        return repository.save(equipamento);
    }

    public List<Equipamento> findAll() {
        return repository.findAll();
    }
}
