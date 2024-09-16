package com.thinkconstructive.restdemospringweb.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelSheet;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;


@ExcelSheet("Hoja1")
public class Objetos {

    @ExcelCell(0)
    private Integer operario;
    @ExcelCell(1)
    private String fecha;
    @ExcelCell(2)
    private Integer numeroOperariones;
    @ExcelCell(3)
    private String turno;

    public Objetos(Integer operario, String fecha, Integer numeroOperariones, String turno) {
        this.operario = operario;
        this.fecha = fecha;
        this.numeroOperariones = numeroOperariones;
        this.turno = turno;
    }

    public Objetos() {
    }

    public Integer getOperario() {
        return operario;
    }

    public void setOperario(Integer operario) {
        this.operario = operario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getNumeroOperariones() {
        return numeroOperariones;
    }

    public void setNumeroOperariones(Integer numeroOperariones) {
        this.numeroOperariones = numeroOperariones;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Objetos{" +
                "operario=" + operario +
                ", fecha='" + fecha + '\'' +
                ", numeroOperariones=" + numeroOperariones +
                ", turno='" + turno + '\'' +
                '}';
    }
}
