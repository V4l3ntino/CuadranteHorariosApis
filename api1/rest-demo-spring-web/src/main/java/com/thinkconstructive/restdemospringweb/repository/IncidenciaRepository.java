package com.thinkconstructive.restdemospringweb.repository;

import com.thinkconstructive.restdemospringweb.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {
}
