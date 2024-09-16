package com.thinkconstructive.restdemospringweb.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelSheet;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@ExcelSheet("Hoja2")
public class JornadasExcel {
    @ExcelCell(0)
    private String nombre;
    @ExcelCell(1)
    private String horario;

    private String apellido;

    private LocalTime horaInicio;

    private LocalTime horaFin;
    private Integer id;



    public JornadasExcel() {
    }

    public JornadasExcel(String nombre, String horario) {
        this.nombre = nombre;
        this.horario = horario;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "JornadasExcel{" +
                "nombre='" + nombre + '\'' +
                ", horario='" + horario + '\'' +
                ", apellido='" + apellido + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}
