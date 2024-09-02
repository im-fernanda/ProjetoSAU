package com.projetosau.repository;

import com.projetosau.domain.Comarca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComarcaRepository extends JpaRepository<Comarca, String> {
    boolean existsByNome(String nome);
}
