package com.thinkconstructive.restdemospringweb.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelSheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ExcelSheet("Detalle de referencias")
public class CodigoArticulo {
    @ExcelCell(0)
    private Integer codigoArticulo;
    @ExcelCell(5)
    private Integer unidadesHoraTeorica;
    private Double tasaMedia;
    private List<Double> listaUnidades = new ArrayList<>();
    private Integer unidadesHoraReal;

    public CodigoArticulo() {
    }

    public CodigoArticulo(Integer codigoArticulo, Integer unidadesHoraTeorica, Double tasaMedia, List<Double> listaUnidades, Integer unidadesHoraReal) {
        this.codigoArticulo = codigoArticulo;
        this.unidadesHoraTeorica = unidadesHoraTeorica;
        this.tasaMedia = tasaMedia;
        this.listaUnidades = listaUnidades;
        this.unidadesHoraReal = unidadesHoraReal;
    }

    @Override
    public String toString() {
        return "CodigoArticulo{" +
                "codigoArticulo=" + codigoArticulo +
                ", unidadesHoraTeorica=" + unidadesHoraTeorica +
                ", tasaMedia=" + tasaMedia +
                ", listaUnidades=" + listaUnidades +
                ", unidadesHoraReal=" + unidadesHoraReal +
                '}';
    }

    public Integer getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(Integer codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public Integer getUnidadesHoraTeorica() {
        return unidadesHoraTeorica;
    }

    public void setUnidadesHoraTeorica(Integer unidadesHoraTeorica) {
        this.unidadesHoraTeorica = unidadesHoraTeorica;
    }

    public Double getTasaMedia() {
        return tasaMedia;
    }

    public void setTasaMedia(Double tasaMedia) {
        this.tasaMedia = tasaMedia;
    }

    public List<Double> getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(List<Double> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public Integer getUnidadesHoraReal() {
        return unidadesHoraReal;
    }

    public void setUnidadesHoraReal(Integer unidadesHoraReal) {
        this.unidadesHoraReal = unidadesHoraReal;
    }
}
