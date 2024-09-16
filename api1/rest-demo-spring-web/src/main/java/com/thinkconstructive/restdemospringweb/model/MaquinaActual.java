package com.thinkconstructive.restdemospringweb.model;


import com.poiji.annotation.ExcelCell;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "operarios_maquina_actual")
public class MaquinaActual {
    @Id
    private Integer id;
    @ExcelCell(0)
    @Column(name = "fecha_trabajo")
    private String fechaTrabajo;
    @Column(name = "id_operario")
    @ExcelCell(1)
    private Integer idOperario;
    @ExcelCell(2)
    @Column(name = "grupo_maquina")
    private String maquina;

    public MaquinaActual() {
    }

    public MaquinaActual(Integer id, String fechaTrabajo, Integer idOperario, String maquina) {
        this.id = id;
        this.fechaTrabajo = fechaTrabajo;
        this.idOperario = idOperario;
        this.maquina = maquina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MaquinaActual{" +
                "fechaTrabajo='" + fechaTrabajo + '\'' +
                ", idOperario=" + idOperario +
                ", maquina='" + maquina + '\'' +
                '}';
    }

    public String getFechaTrabajo() {
        return fechaTrabajo;
    }

    public void setFechaTrabajo(String fechaTrabajo) {
        this.fechaTrabajo = fechaTrabajo;
    }

    public Integer getIdOperario() {
        return idOperario;
    }

    public void setIdOperario(Integer idOperario) {
        this.idOperario = idOperario;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }
}
