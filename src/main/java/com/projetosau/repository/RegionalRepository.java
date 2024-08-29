package com.projetosau.repository;

import com.projetosau.domain.Regional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionalRepository extends JpaRepository<Regional, String> {

}
