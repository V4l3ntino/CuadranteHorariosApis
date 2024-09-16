package com.thinkconstructive.restdemospringweb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Operario {

    private Integer id;
    private Double manana = 0.0;
    private Double tarde = 0.0;
    private Double noche = 0.0;

    private Double porcentajeManana;
    private Double porcentajeTarde;
    private Double porcentajeNoche;

    public Operario() {
    }

    public Operario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getManana() {
        return manana;
    }

    public void setManana(Double manana) {
        this.manana += manana;
    }

    public Double getTarde() {
        return tarde;
    }

    public void setTarde(Double tarde) {
        this.tarde += tarde;
    }

    public Double getNoche() {
        return noche;
    }

    public void setNoche(Double noche) {
        this.noche += noche;
    }

    public Double getPorcentajeManana() {
        return porcentajeManana;
    }

    public void setPorcentajeManana(Double porcentajeManana) {
        this.porcentajeManana = (double) Math.round(porcentajeManana/(this.manana + this.tarde + this.noche)*100);
    }

    public Double getPorcentajeTarde() {
        return porcentajeTarde;
    }

    public void setPorcentajeTarde(Double porcentajeTarde) {
        this.porcentajeTarde = (double) Math.round(porcentajeTarde/(this.manana + this.tarde + this.noche)*100);
    }

    public Double getPorcentajeNoche() {
        return porcentajeNoche;
    }

    public void setPorcentajeNoche(Double porcentajeNoche) {
        this.porcentajeNoche = (double) Math.round(porcentajeNoche/(this.manana + this.tarde + this.noche)*100);
    }

    @Override
    public String toString() {
        return "Operario{" +
                "id=" + id +
                ", porcentajeManana=" + porcentajeManana +
                ", porcentajeTarde=" + porcentajeTarde +
                ", porcentajeNoche=" + porcentajeNoche +
                '}';
    }
}
