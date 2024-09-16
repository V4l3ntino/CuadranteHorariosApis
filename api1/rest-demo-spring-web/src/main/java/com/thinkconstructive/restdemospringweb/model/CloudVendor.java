package com.thinkconstructive.restdemospringweb.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelSheet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ExcelSheet("Hoja2")
@Table(name="blogapp_user")
public class CloudVendor {
    @ExcelCell(0)
    @Id
    private Integer id;
    @ExcelCell(1)
    @Column(name = "nombre")
    private String nombre;
    @ExcelCell(2)
    @Column(name = "maquina")
    private String maquina;

    @Column(name = "turno")
    private String turno;

    @Column(name = "categoria")
    private String categoria;
    @ExcelCell(3)
    @Column(name = "conocimientos")
    private String conocimientos;

    @Column(name = "evento")
    private Boolean evento = false;

    @Column(name = "permutado")
    private Boolean permutado = false;

    @ExcelCell(4)
    @Column( name = "fecha_inicio")
    private String fechaInicio;
    @ExcelCell(5)
    @Column(name = "fecha_baja")
    private String fechaBaja;
    @Column(name = "update_turno")
    private Boolean updateTurno = false;
    @Column(name = "expediente")
    private Integer expediente = 0;
    @Column(name = "faltas")
    private Integer faltas = 0;

    public CloudVendor() {
    }

    public CloudVendor(Integer id, String nombre, String maquina, String turno, String categoria, String conocimientos, Boolean evento) {
        this.id = id;
        this.nombre = nombre;
        this.maquina = maquina;
        this.turno = turno;
        this.categoria = categoria;
        this.conocimientos = conocimientos;
        this.evento = evento;
    }

    public CloudVendor(Integer id, String nombre, String maquina, String turno, String categoria, String conocimientos, String fechaInicio, String fechaBaja) {
        this.id = id;
        this.nombre = nombre;
        this.maquina = maquina;
        this.turno = turno;
        this.categoria = categoria;
        this.conocimientos = conocimientos;
        this.fechaInicio = fechaInicio;
        this.fechaBaja = fechaBaja;
    }

    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }

    public Boolean getPermutado() {
        return permutado;
    }

    public void setPermutado(Boolean permutado) {
        this.permutado = permutado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getConocimientos() {
        return conocimientos;
    }

    public void setConocimientos(String conocimientos) {
        this.conocimientos = conocimientos;
    }

    public Boolean getEvento() {
        return evento;
    }

    public void setEvento(Boolean evento) {
        this.evento = evento;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Boolean getUpdateTurno() {
        return updateTurno;
    }

    public void setUpdateTurno(Boolean updateTurno) {
        this.updateTurno = updateTurno;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    @Override
    public String toString() {
        return "CloudVendor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", maquina='" + maquina + '\'' +
                ", turno='" + turno + '\'' +
                ", categoria='" + categoria + '\'' +
                ", conocimientos='" + conocimientos + '\'' +
                ", evento=" + evento +
                ", permutado=" + permutado +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaBaja='" + fechaBaja + '\'' +
                ", updateTurno=" + updateTurno +
                '}';
    }
}
