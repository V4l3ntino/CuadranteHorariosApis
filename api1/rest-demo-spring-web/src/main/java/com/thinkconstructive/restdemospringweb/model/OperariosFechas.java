package com.thinkconstructive.restdemospringweb.model;


import com.poiji.annotation.ExcelCell;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operarios_fechas")
public class OperariosFechas {
    @ExcelCell(0)
    @Id
    private Integer operario_id;
    @ExcelCell(1)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha_alta")
    @ExcelCell(2)
    private String fechaAlta;
    @ExcelCell(3)
    @Column(name = "fecha_baja")
    private String fechaBaja;
    @Column(name = "tipo")
    @ExcelCell(4)
    private String tipoOperario;
    @Transient
    private Boolean tieneMaquina;
    @Transient
    private String maquina;
    @Transient
    private String conocimiento;
    @Transient
    private List<OperariosMaquina> listaMaquinas = new ArrayList<>();

    public OperariosFechas() {
    }

    public OperariosFechas(Integer id, String nombre, String fechaAlta, String fechaBaja, String tipoOperario) {
        this.operario_id = id;
        this.nombre = nombre;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.tipoOperario = tipoOperario;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getConocimiento() {
        return conocimiento;
    }

    public void setConocimiento(String conocimiento) {
        this.conocimiento = conocimiento;
    }

    public List<OperariosMaquina> getListaMaquinas() {
        return listaMaquinas;
    }

    public void setListaMaquinas(OperariosMaquina maquina) {
        this.listaMaquinas.add(maquina);
    }

    public Boolean getTieneMaquina() {
        return tieneMaquina;
    }

    public void setTieneMaquina(Boolean tieneMaquina) {
        this.tieneMaquina = tieneMaquina;
    }


    @Override
    public String toString() {
        return "OperariosFechas{" +
                "id=" + operario_id +
                ", nombre='" + nombre + '\'' +
                ", fechaAlta='" + fechaAlta + '\'' +
                ", fechaBaja='" + fechaBaja + '\'' +
                ", tipoOperario='" + tipoOperario + '\'' +
                ", tieneMaquina=" + tieneMaquina +
                ", listaMaquinas=" + listaMaquinas +
                '}';
    }

    public Integer getOperario_id() {
        return operario_id;
    }

    public void setOperario_id(Integer operario_id) {
        this.operario_id = operario_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getTipoOperario() {
        return tipoOperario;
    }

    public void setTipoOperario(String tipoOperario) {
        this.tipoOperario = tipoOperario;
    }
}
