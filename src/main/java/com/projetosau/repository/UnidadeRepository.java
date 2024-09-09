package com.projetosau.repository;

import com.projetosau.domain.Comarca;
import com.projetosau.domain.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    boolean existsByNome(String nome);

    List<Unidade> findByComarca(Comarca comarca);

}

