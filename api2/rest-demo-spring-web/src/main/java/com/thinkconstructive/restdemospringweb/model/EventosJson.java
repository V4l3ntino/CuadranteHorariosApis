package com.thinkconstructive.restdemospringweb.model;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EventosJson {
    private Integer id_evento;
    private String lafechaInicio;
    private String lafechaFin;
    private String turnoActualizado;
    private String observaciones;
    private UUID id_local = UUID.randomUUID();

    public EventosJson() {
    }

    public EventosJson(Integer trabajador_id, String fecha_inicio, String fecha_fin, String turno_actualizado, String observaciones) {
        this.id_evento = trabajador_id;
        this.lafechaInicio = fecha_inicio;
        this.lafechaFin = fecha_fin;
        this.turnoActualizado = turno_actualizado;
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public UUID getId_local() {
        return id_local;
    }

    public void setId_local(UUID id_local) {
        this.id_local = id_local;
    }

    public Integer getId_evento() {
        return id_evento;
    }

    public void setId_evento(Integer id_evento) {
        this.id_evento = id_evento;
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

    @Override
    public String toString() {
        return "EventosJson{" +
                "id_evento=" + id_evento +
                ", lafechaInicio='" + lafechaInicio + '\'' +
                ", lafechaFin='" + lafechaFin + '\'' +
                ", turnoActualizado='" + turnoActualizado + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", id_local=" + id_local +
                '}';
    }
}
