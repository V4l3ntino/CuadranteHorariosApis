package com.thinkconstructive.restdemospringweb.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "vacaciones_humano")
public class Eventos {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "empresa_codigo_terminal")
    private Integer empresa_codigo_terminal;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date lafechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date lafechaFin;


    @Column(name = "nombre")
    private String turnoActualizado;
    @Transient
    private String observaciones;

    public Eventos() {
    }

    public Eventos(Integer id, Integer empresa_codigo_terminal, Date lafechaInicio, Date lafechaFin, String turnoActualizado, String observaciones) {
        this.id = id;
        this.empresa_codigo_terminal = empresa_codigo_terminal;
        this.lafechaInicio = lafechaInicio;
        this.lafechaFin = lafechaFin;
        this.turnoActualizado = turnoActualizado;
        this.observaciones = observaciones;
    }


    public Integer getEmpresa_codigo_terminal() {
        return empresa_codigo_terminal;
    }

    public void setEmpresa_codigo_terminal(Integer empresa_codigo_terminal) {
        this.empresa_codigo_terminal = empresa_codigo_terminal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


    public Date getLafechaInicio() {
        return lafechaInicio;
    }


    public void setLafechaInicio(Date lafechaInicio) {
        this.lafechaInicio = lafechaInicio;
    }

    public Date getLafechaFin() {
        return lafechaFin;
    }

    public void setLafechaFin(Date lafechaFin) {
        this.lafechaFin = lafechaFin;
    }

    public String getTurnoActualizado() {
        return turnoActualizado;
    }

    public void setTurnoActualizado(String turnoActualizado) {
        this.turnoActualizado = turnoActualizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Eventos{" +
                "id=" + id +
                ", empresa_codigo_terminal=" + empresa_codigo_terminal +
                ", lafechaInicio=" + lafechaInicio +
                ", lafechaFin=" + lafechaFin +
                ", turnoActualizado='" + turnoActualizado + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
