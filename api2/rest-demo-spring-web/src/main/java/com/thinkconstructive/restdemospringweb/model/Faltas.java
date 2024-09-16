package com.thinkconstructive.restdemospringweb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "blogapp_faltas")
public class Faltas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_evento")
    private Integer id;
    @Column(name = "usuario_id")
    private Integer usuario_id;
    @Column(name = "fecha_inicio")
    private String lafechaInicio;
    @Column(name = "fecha_fin")
    private String lafechaFin;
    @Column(name = "turno_actualizado")
    private String turnoActualizado;
    @Column(name = "creador_id")
    private Integer creador;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "orden")
    private Integer orden;

    public Faltas() {
    }

    public Faltas(Integer id, Integer usuario_id, String lafechaInicio, String lafechaFin, String turnoActualizado,  String observaciones, Integer orden) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.lafechaInicio = lafechaInicio;
        this.lafechaFin = lafechaFin;
        this.turnoActualizado = turnoActualizado;
        this.observaciones = observaciones;
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "Faltas{" +
                "id=" + id +
                ", usuario_id=" + usuario_id +
                ", lafechaInicio='" + lafechaInicio + '\'' +
                ", lafechaFin='" + lafechaFin + '\'' +
                ", turnoActualizado='" + turnoActualizado + '\'' +
                ", creador=" + creador +
                ", observaciones='" + observaciones + '\'' +
                ", orden=" + orden +
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

    public String getLafechaInicio() {
        return lafechaInicio;
    }

    public void setLafechaInicio(String lafechaInicio) {
        this.lafechaInicio = lafechaInicio;
    }

    public String getLafechaFin() {
        return lafechaFin;
    }

    public void setLafechaFin(String lafechaFin) {
        this.lafechaFin = lafechaFin;
    }

    public String getTurnoActualizado() {
        return turnoActualizado;
    }

    public void setTurnoActualizado(String turnoActualizado) {
        this.turnoActualizado = turnoActualizado;
    }

    public Integer getCreador() {
        return creador;
    }

    public void setCreador(Integer creador) {
        this.creador = creador;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}
