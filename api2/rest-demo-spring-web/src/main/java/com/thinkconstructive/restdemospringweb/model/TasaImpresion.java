package com.thinkconstructive.restdemospringweb.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelSheet;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Component;

@Component
@ExcelSheet("sheet1")
public class TasaImpresion {
    @ExcelCell(0)
    private String maquina;
    @ExcelCell(1)
    private Integer idPedido;
    @ExcelCell(2)
    private Integer unidadesFabricadas;
    @ExcelCell(3)
    private Double duracionHoras;
    @ExcelCell(4)
    private Integer codigoArticulo;

    private Double segundosporUnidad;
    private Double unidadesporMinuto;
    private Double unidadesporHora;

    private Boolean excepcion = false;
    private Boolean malaOperacion = false;

    public TasaImpresion() {
    }

    public TasaImpresion(String maquina, Integer idPedido, Integer unidadesFabricadas, Double duracionHoras, Integer codigoArticulo) {
        this.maquina = maquina;
        this.idPedido = idPedido;
        this.unidadesFabricadas = unidadesFabricadas;
        this.duracionHoras = duracionHoras;
        this.codigoArticulo = codigoArticulo;
    }

    @Override
    public String toString() {
        return "TasaImpresion{" +
                "maquina='" + maquina + '\'' +
                ", idPedido=" + idPedido +
                ", unidadesFabricadas=" + unidadesFabricadas +
                ", duracionHoras=" + duracionHoras +
                ", codigoArticulo=" + codigoArticulo +
                ", segundosporUnidad=" + segundosporUnidad +
                ", unidadesporMinuto=" + unidadesporMinuto +
                ", unidadesporHora=" + unidadesporHora +
                ", malaOperación=" + malaOperacion+
                ", excepCión=" + excepcion+
                ", comprobacion=Dato real: "+redondeo(duracionHoras)+" Dato obtenido: "+redondeo(((this.segundosporUnidad * this.unidadesFabricadas)/ (double) 60)/ (double) 60)+
                '}';
    }

    public Boolean getMalaOperacion() {
        return malaOperacion;
    }

    public void setMalaOperacion(Boolean malaOperacion) {
        this.malaOperacion = malaOperacion;
    }

    public Boolean getExcepcion() {
        return excepcion;
    }

    public void setExcepcion(Boolean excepcion) {
        this.excepcion = excepcion;
    }

    public Double getUnidadesporHora() {
        return unidadesporHora;
    }

    public void setUnidadesporHora() {
        this.unidadesporHora = (this.unidadesporMinuto * 60);
        this.unidadesporHora = redondeo(this.unidadesporHora);
    }

    public Double getUnidadesporMinuto() {
        return unidadesporMinuto;
    }

    public void setUnidadesporMinuto() {
        this.unidadesporMinuto = (double) Math.round(((float) 60 / this.segundosporUnidad));
        this.unidadesporMinuto = redondeo(this.unidadesporMinuto);
    }

    public Double getSegundosporUnidad() {
        return segundosporUnidad;
    }

    public void setSegundosporUnidad() {
        Integer minutos = (int) Math.round(this.duracionHoras*60);
        this.segundosporUnidad = ((double) minutos/ this.unidadesFabricadas) * 60;
        this.setUnidadesporMinuto();
        this.setUnidadesporHora();
        this.comprobacion();
    }

    public void comprobacion(){
       Double horas = ((this.segundosporUnidad * this.unidadesFabricadas)/ (double) 60)/ (double) 60;
       horas = redondeo(horas);
       Double duracionHoras = redondeo(this.duracionHoras);
       if(!(horas <= duracionHoras + 0.2 && horas >= duracionHoras - 0.2)){
           setMalaOperacion(true);
       }
    }

    public String getMaquina() {
        return maquina;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public Integer getUnidadesFabricadas() {
        return unidadesFabricadas;
    }

    public Double getDuracionHoras() {
        return duracionHoras;
    }

    public Integer getCodigoArticulo() {
        return codigoArticulo;
    }

    public Double redondeo(Double numero){
        return Math.round(numero * 10) / 10.0;
    }
}
