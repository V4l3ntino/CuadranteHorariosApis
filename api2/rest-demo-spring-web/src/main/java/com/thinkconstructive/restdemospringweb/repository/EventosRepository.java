package com.thinkconstructive.restdemospringweb.repository;

import com.thinkconstructive.restdemospringweb.model.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventosRepository extends JpaRepository<Eventos, Integer> {

}
