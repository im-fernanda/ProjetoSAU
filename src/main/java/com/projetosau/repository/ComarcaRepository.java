package com.projetosau.repository;

import com.projetosau.domain.Comarca;
import com.projetosau.domain.Regional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComarcaRepository extends JpaRepository<Comarca, Long> {
    boolean existsByNome(String nome);

    public List<Comarca> findByRegional(Regional regional);
}
