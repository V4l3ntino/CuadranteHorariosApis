package com.thinkconstructive.restdemospringweb.model;


import com.poiji.annotation.ExcelCell;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operarios_maquinas_anio_entero")
public class OperariosMaquina {
    @Id
    private Integer id;

    @ExcelCell(0)
    @Column(name = "operario_id")
    private Integer operario_id;
    @ExcelCell(1)
    @Column(name = "operacion")
    private String operacion;
    @ExcelCell(2)
    @Column(name = "autos")
    private Integer autos;
    @ExcelCell(3)
    @Column(name = "bordadoras")
    private Integer bordados;
    @ExcelCell(4)
    @Column(name = "cosido")
    private Integer cosigos;
    @ExcelCell(5)
    @Column(name = "digital")
    private Integer digital;
    @ExcelCell(6)
    @Column(name = "horno")
    private Integer horno;
    @ExcelCell(7)
    @Column(name = "laser")
    private Integer laser;
    @ExcelCell(8)
    @Column(name = "otros")
    private Integer otros;
    @ExcelCell(9)
    @Column(name = "planchas")
    private Integer planchas;
    @ExcelCell(10)
    @Column(name = "sublimacion")
    private Integer sublimacion;
    @ExcelCell(11)
    @Column(name = "pulpos")
    private Integer pulpos;
    @ExcelCell(12)
    @Column(name = "tampografia")
    private Integer tampografia;
    @ExcelCell(13)
    @Column(name = "termograbado")
    private Integer termo;

    public OperariosMaquina() {
    }

    public OperariosMaquina(Integer id, String operacion, Integer autos, Integer bordados, Integer cosigos, Integer digital, Integer horno, Integer laser, Integer otros, Integer planchas, Integer sublimacion, Integer pulpos, Integer tampografia, Integer termo) {
        this.operario_id = id;
        this.operacion = operacion;
        this.autos = autos;
        this.bordados = bordados;
        this.cosigos = cosigos;
        this.digital = digital;
        this.horno = horno;
        this.laser = laser;
        this.otros = otros;
        this.planchas = planchas;
        this.sublimacion = sublimacion;
        this.pulpos = pulpos;
        this.tampografia = tampografia;
        this.termo = termo;
    }

    @Override
    public String toString() {
        return "OperariosTurnos{" +
                "id=" + operario_id +
                ", operacion='" + operacion + '\'' +
                ", autos=" + autos +
                ", bordados=" + bordados +
                ", cosigos=" + cosigos +
                ", digital=" + digital +
                ", horno=" + horno +
                ", laser=" + laser +
                ", otros=" + otros +
                ", planchas=" + planchas +
                ", sublimacion=" + sublimacion +
                ", pulpos=" + pulpos +
                ", tampografia=" + tampografia +
                ", termo=" + termo +
                '}';
    }
    public List<Integer> getAllCantidades(){
        List<Integer> lista = new ArrayList<>();
        lista.add(this.getAutos());
        lista.add(this.getBordados());
        lista.add(this.getCosigos());
        lista.add(this.getDigital());
        lista.add(this.getHorno());
        lista.add(this.getLaser());
        lista.add(this.getOtros());
        lista.add(this.getPlanchas());
        lista.add(this.getSublimacion());
        lista.add(this.getPulpos());
        lista.add(this.getTampografia());
        lista.add(this.getTermo());
        return lista;
    }

    public Integer getOperario_id() {
        return operario_id;
    }

    public void setOperario_id(Integer operario_id) {
        this.operario_id = operario_id;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Integer getAutos() {
        return autos;
    }

    public void setAutos(Integer autos) {
        this.autos = autos;
    }

    public Integer getBordados() {
        return bordados;
    }

    public void setBordados(Integer bordados) {
        this.bordados = bordados;
    }

    public Integer getCosigos() {
        return cosigos;
    }

    public void setCosigos(Integer cosigos) {
        this.cosigos = cosigos;
    }

    public Integer getDigital() {
        return digital;
    }

    public void setDigital(Integer digital) {
        this.digital = digital;
    }

    public Integer getHorno() {
        return horno;
    }

    public void setHorno(Integer horno) {
        this.horno = horno;
    }

    public Integer getLaser() {
        return laser;
    }

    public void setLaser(Integer laser) {
        this.laser = laser;
    }

    public Integer getOtros() {
        return otros;
    }

    public void setOtros(Integer otros) {
        this.otros = otros;
    }

    public Integer getPlanchas() {
        return planchas;
    }

    public void setPlanchas(Integer planchas) {
        this.planchas = planchas;
    }

    public Integer getSublimacion() {
        return sublimacion;
    }

    public void setSublimacion(Integer sublimacion) {
        this.sublimacion = sublimacion;
    }

    public Integer getPulpos() {
        return pulpos;
    }

    public void setPulpos(Integer pulpos) {
        this.pulpos = pulpos;
    }

    public Integer getTampografia() {
        return tampografia;
    }

    public void setTampografia(Integer tampografia) {
        this.tampografia = tampografia;
    }

    public Integer getTermo() {
        return termo;
    }

    public void setTermo(Integer termo) {
        this.termo = termo;
    }
}
