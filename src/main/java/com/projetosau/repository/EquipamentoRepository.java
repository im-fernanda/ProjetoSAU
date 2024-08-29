package com.projetosau.repository;

import com.projetosau.domain.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipamentoRepository extends JpaRepository<Equipamento, String> {
    List<Equipamento> findByIsDeletedNull();
    boolean existsByTombo(String tombo);
}