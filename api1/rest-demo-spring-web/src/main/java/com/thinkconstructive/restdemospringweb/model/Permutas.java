package com.thinkconstructive.restdemospringweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blogapp_permutado")
public class Permutas {
    @Id
    @Column(name = "id_permutado")
    private Integer id;
    @Column(name = "usuario_id")
    private Integer usuario_id;

    public Permutas() {
    }

    public Permutas(Integer id, Integer usuario_id) {
        this.id = id;
        this.usuario_id = usuario_id;
    }

    @Override
    public String toString() {
        return "Permutas{" +
                "id=" + id +
                ", usuario_id=" + usuario_id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }
}
