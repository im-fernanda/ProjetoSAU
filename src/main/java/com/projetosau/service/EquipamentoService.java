package com.projetosau.service;

import org.springframework.stereotype.Service;
import com.projetosau.domain.Equipamento;
import com.projetosau.domain.Unidade;
import com.projetosau.repository.EquipamentoRepository;
import com.projetosau.repository.UnidadeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {

    private final EquipamentoRepository repository;
    private final UnidadeRepository unidadeRepository;

    public EquipamentoService(EquipamentoRepository repository, UnidadeRepository unidadeRepository) {
        this.repository = repository;
        this.unidadeRepository = unidadeRepository;
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

    public Optional<Equipamento> findByTombo(Integer tombo) {
        return repository.findByTombo(tombo);
    }

    public List<Equipamento> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Equipamento> findByUnidadeId(Long unidadeId) {
        return repository.findByUnidadeId(unidadeId);
    }

    public Equipamento transferirEquipamento(Long equipamentoId, Long novaUnidadeId) {
        Equipamento equipamento = repository.findById(equipamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado."));

        Unidade novaUnidade = unidadeRepository.findById(novaUnidadeId)
                .orElseThrow(() -> new IllegalArgumentException("Unidade de destino não encontrada."));

        if (equipamento.getUnidade().getId().equals(novaUnidadeId)) {
            throw new IllegalArgumentException("O equipamento já está nesta unidade.");
        }

        equipamento.setUnidade(novaUnidade);
        return repository.saveAndFlush(equipamento);
    }
}
