package com.thinkconstructive.restdemospringweb.repository;

import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CloudVendorRepository extends JpaRepository<CloudVendor, Integer> {
    @Query(value = "SELECT * FROM blogapp_user WHERE nombre LIKE %:nombre%", nativeQuery = true)
    CloudVendor findUserbySurname(@Param("nombre") String nombre);

}
