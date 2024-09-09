package com.projetosau.repository;

import com.projetosau.domain.Regional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionalRepository extends JpaRepository<Regional, Long> {
    boolean existsByNome(String nome);
}
