package com.thinkconstructive.restdemospringweb.repository;

import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import com.thinkconstructive.restdemospringweb.model.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventosRepository extends JpaRepository<Eventos, Integer> {
}
