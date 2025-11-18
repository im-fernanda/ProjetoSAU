package com.projetosau.repository;

import com.projetosau.domain.Equipamento;
import com.projetosau.domain.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    boolean existsByTombo(Integer tombo);
    
    Optional<Equipamento> findByTombo(Integer tombo);
    
    List<Equipamento> findByNomeContainingIgnoreCase(String nome);
    
    List<Equipamento> findByUnidade(Unidade unidade);
    
    List<Equipamento> findByUnidadeId(Long unidadeId);
}