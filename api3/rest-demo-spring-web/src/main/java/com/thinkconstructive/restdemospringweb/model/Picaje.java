package com.thinkconstructive.restdemospringweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "picaje")
public class Picaje {
    @Id
    private Integer id;
    @Column(name = "Fecha_Inicio")
    private String fechaInicio;
    @Column(name = "Operario")
    private Integer operario;
    @Column(name = "Maquina")
    private String maquina;

    public Picaje() {
    }

    public Picaje(Integer id, String fechaInicio, Integer operario, String maquina) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.operario = operario;
        this.maquina = maquina;
    }

    @Override
    public String toString() {
        return "Picaje{" +
                "id=" + id +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", operario=" + operario +
                ", maquina='" + maquina + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getOperario() {
        return operario;
    }

    public void setOperario(Integer operario) {
        this.operario = operario;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }
}
