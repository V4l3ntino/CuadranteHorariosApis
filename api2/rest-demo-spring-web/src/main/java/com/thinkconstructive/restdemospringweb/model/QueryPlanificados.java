package com.thinkconstructive.restdemospringweb.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelSheet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@ExcelSheet("QueryPlanificacion")
public class QueryPlanificados {
    @ExcelCell(0)
    private String ejercicioFabricacion;
    @ExcelCell(1)
    private String fechaPlanificada;
    @ExcelCell(2)
    private Integer numeroFabricacionSap;
    @ExcelCell(3)
    private Integer codigoArticulo;
    @ExcelCell(4)
    private String descripcionArticulo;
    @ExcelCell(5)
    private Integer unidadesFabricar;
    @ExcelCell(6)
    private Integer materialPadreSap;
    @ExcelCell(7)
    private String grupoMaquina;
    @ExcelCell(8)
    private String maquinaPlanificada;
    @ExcelCell(9)
    private String maquina;
    @ExcelCell(10)
    private String orden;
    @ExcelCell(11)
    private String fechaFinPreparacion;
    @ExcelCell(12)
    private String fechaInicioImpresion;
    @ExcelCell(13)
    private String fechaFinImpresion;

    private LocalTime horaPreparacion;
    private LocalTime horaIniImpresion;
    private LocalTime horaFinImpresion;

    private LocalDate dateFechaPreparacion;
    private LocalDate dateFechaIniImpresion;
    private LocalDate dateFechaFImpresion;

    public QueryPlanificados() {
    }

    public QueryPlanificados(String ejercicioFabricacion, String fechaPlanificada, Integer numeroFabricacionSap, Integer codigoArticulo, String descripcionArticulo, Integer unidadesFabricar, Integer materialPadreSap, String grupoMaquina, String maquinaPlanificada, String maquina, String orden, String fechaFinPreparacion, String fechaInicioImpresion, String fechaFinImpresion) {
        this.ejercicioFabricacion = ejercicioFabricacion;
        this.fechaPlanificada = fechaPlanificada;
        this.numeroFabricacionSap = numeroFabricacionSap;
        this.codigoArticulo = codigoArticulo;
        this.descripcionArticulo = descripcionArticulo;
        this.unidadesFabricar = unidadesFabricar;
        this.materialPadreSap = materialPadreSap;
        this.grupoMaquina = grupoMaquina;
        this.maquinaPlanificada = maquinaPlanificada;
        this.maquina = maquina;
        this.orden = orden;
        this.fechaFinPreparacion = fechaFinPreparacion;
        this.fechaInicioImpresion = fechaInicioImpresion;
        this.fechaFinImpresion = fechaFinImpresion;
    }

    public LocalDate getDateFechaPreparacion() {
        return dateFechaPreparacion;
    }

    public void setDateFechaPreparacion(LocalDate dateFechaPreparacion) {
        this.dateFechaPreparacion = dateFechaPreparacion;
    }

    public LocalDate getDateFechaIniImpresion() {
        return dateFechaIniImpresion;
    }

    public void setDateFechaIniImpresion(LocalDate dateFechaIniImpresion) {
        this.dateFechaIniImpresion = dateFechaIniImpresion;
    }

    public LocalDate getDateFechaFImpresion() {
        return dateFechaFImpresion;
    }

    public void setDateFechaFImpresion(LocalDate dateFechaFImpresion) {
        this.dateFechaFImpresion = dateFechaFImpresion;
    }

    public LocalTime getHoraPreparacion() {
        return horaPreparacion;
    }

    public void setHoraPreparacion(LocalTime horaPreparacion) {
        this.horaPreparacion = horaPreparacion;
    }

    public LocalTime getHoraIniImpresion() {
        return horaIniImpresion;
    }

    public void setHoraIniImpresion(LocalTime horaIniImpresion) {
        this.horaIniImpresion = horaIniImpresion;
    }

    public LocalTime getHoraFinImpresion() {
        return horaFinImpresion;
    }

    public void setHoraFinImpresion(LocalTime horaFinImpresion) {
        this.horaFinImpresion = horaFinImpresion;
    }

    public String getEjercicioFabricacion() {
        return ejercicioFabricacion;
    }

    public void setEjercicioFabricacion(String ejercicioFabricacion) {
        this.ejercicioFabricacion = ejercicioFabricacion;
    }

    public String getFechaPlanificada() {
        return fechaPlanificada;
    }

    public void setFechaPlanificada(String fechaPlanificada) {
        this.fechaPlanificada = fechaPlanificada;
    }

    public Integer getNumeroFabricacionSap() {
        return numeroFabricacionSap;
    }

    public void setNumeroFabricacionSap(Integer numeroFabricacionSap) {
        this.numeroFabricacionSap = numeroFabricacionSap;
    }

    public Integer getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(Integer codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public Integer getUnidadesFabricar() {
        return unidadesFabricar;
    }

    public void setUnidadesFabricar(Integer unidadesFabricar) {
        this.unidadesFabricar = unidadesFabricar;
    }

    public Integer getMaterialPadreSap() {
        return materialPadreSap;
    }

    public void setMaterialPadreSap(Integer materialPadreSap) {
        this.materialPadreSap = materialPadreSap;
    }

    public String getGrupoMaquina() {
        return grupoMaquina;
    }

    public void setGrupoMaquina(String grupoMaquina) {
        this.grupoMaquina = grupoMaquina;
    }

    public String getMaquinaPlanificada() {
        return maquinaPlanificada;
    }

    public void setMaquinaPlanificada(String maquinaPlanificada) {
        this.maquinaPlanificada = maquinaPlanificada;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getFechaFinPreparacion() {
        return fechaFinPreparacion;
    }

    public void setFechaFinPreparacion(String fechaFinPreparacion) {
        this.fechaFinPreparacion = fechaFinPreparacion;
    }

    public String getFechaInicioImpresion() {
        return fechaInicioImpresion;
    }

    public void setFechaInicioImpresion(String fechaInicioImpresion) {
        this.fechaInicioImpresion = fechaInicioImpresion;
    }

    public String getFechaFinImpresion() {
        return fechaFinImpresion;
    }

    public void setFechaFinImpresion(String fechaFinImpresion) {
        this.fechaFinImpresion = fechaFinImpresion;
    }

    @Override
    public String toString() {
        return "QueryPlanificados{" +
                "ejercicioFabricacion='" + ejercicioFabricacion + '\'' +
                ", fechaPlanificada='" + fechaPlanificada + '\'' +
                ", numeroFabricacionSap=" + numeroFabricacionSap +
                ", codigoArticulo=" + codigoArticulo +
                ", descripcionArticulo='" + descripcionArticulo + '\'' +
                ", unidadesFabricar=" + unidadesFabricar +
                ", materialPadreSap=" + materialPadreSap +
                ", grupoMaquina='" + grupoMaquina + '\'' +
                ", maquinaPlanificada='" + maquinaPlanificada + '\'' +
                ", maquina='" + maquina + '\'' +
                ", orden='" + orden + '\'' +
                ", fechaFinPreparacion='" + fechaFinPreparacion + '\'' +
                ", fechaInicioImpresion='" + fechaInicioImpresion + '\'' +
                ", fechaFinImpresion='" + fechaFinImpresion + '\'' +
                ", horaPreparacion=" + horaPreparacion +
                ", horaIniImpresion=" + horaIniImpresion +
                ", horaFinImpresion=" + horaFinImpresion +
                ", fechaPreparacion=" + dateFechaPreparacion +
                ", fechaIniImpresion=" + dateFechaIniImpresion +
                ", fechaFImpresion=" + dateFechaFImpresion +
                '}';
    }
}
