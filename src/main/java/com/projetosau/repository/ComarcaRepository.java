package com.projetosau.repository;

import com.projetosau.domain.Comarca;
import com.projetosau.domain.Regional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComarcaRepository extends JpaRepository<Comarca, Long> {
    boolean existsByNome(String nome);

    List<Comarca> findByRegional(Regional regional);
}
