package com.thinkconstructive.restdemospringweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blogapp_incidencia")
public class Incidencia {
    @Id
    private Integer id;
    @Column(name = "operario_id")
    private Integer operario_id;

    public Incidencia() {
    }

    public Incidencia(Integer id, Integer operario_id) {
        this.id = id;
        this.operario_id = operario_id;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", operario_id=" + operario_id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperario_id() {
        return operario_id;
    }

    public void setOperario_id(Integer operario_id) {
        this.operario_id = operario_id;
    }
}
