package com.thinkconstructive.restdemospringweb.repository;

import com.thinkconstructive.restdemospringweb.model.HorarioTrabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HorarioTrabajadorRepository extends JpaRepository<HorarioTrabajador, String> {

    @Query(value = "select * from horarios_trabajador where empresa_codigo_terminal = :id", nativeQuery = true)
    List<HorarioTrabajador> findAllById(@Param("id") Integer id);
}
