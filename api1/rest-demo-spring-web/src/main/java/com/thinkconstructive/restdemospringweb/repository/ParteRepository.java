package com.thinkconstructive.restdemospringweb.repository;

import com.thinkconstructive.restdemospringweb.model.Parte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParteRepository extends JpaRepository<Parte, Integer> {
}
