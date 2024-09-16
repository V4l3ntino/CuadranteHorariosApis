package com.thinkconstructive.restdemospringweb.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelSheet;
import org.springframework.stereotype.Component;

@Component
@ExcelSheet("Ausencias")
public class EventosExcel {
    @ExcelCell(0)
    public Integer codigoTrabajador;
    @ExcelCell(1)
    public String nombre;
    @ExcelCell(2)
    public String fechaInicio;
    @ExcelCell(3)
    public String fechaFin;
    @ExcelCell(4)
    public String turno;
    public String apellido;

    public EventosExcel() {
    }

    public EventosExcel(Integer codigoTrabajador, String nombre, String fechaInicio, String fechaFin, String turno) {
        this.codigoTrabajador = codigoTrabajador;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.turno = turno;
    }

    public Integer getCodigoTrabajador() {
        return codigoTrabajador;
    }

    public void setCodigoTrabajador(Integer codigoTrabajador) {
        this.codigoTrabajador = codigoTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "EventosExcel{" +
                "codigoTrabajador=" + codigoTrabajador +
                ", nombre='" + nombre + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", turno='" + turno + '\'' +
                '}';
    }
}
