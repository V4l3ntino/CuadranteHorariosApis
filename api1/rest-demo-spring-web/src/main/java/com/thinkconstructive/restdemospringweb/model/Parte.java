package com.thinkconstructive.restdemospringweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blogapp_parte")
public class Parte {
    @Id
    private Integer id;
    @Column(name = "operario_id")
    private Integer operario_id;

    public Parte() {
    }

    public Parte(Integer id, Integer operario_id) {
        this.id = id;
        this.operario_id = operario_id;
    }

    @Override
    public String toString() {
        return "Parte{" +
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
