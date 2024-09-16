package com.thinkconstructive.restdemospringweb;

import com.poiji.bind.Poiji;
import com.thinkconstructive.restdemospringweb.model.QueryPlanificados;
import jakarta.annotation.PostConstruct;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class QueryPlanificadosApp {
    public static void main(String[] args){
        SpringApplication.run(QueryPlanificadosApp.class, args);
    }

    @PostConstruct
    public void init(){
        File input = new File("QueryPlanificados.xlsx");
        List<QueryPlanificados> lista = Poiji.fromExcel(input, QueryPlanificados.class);
        Set<LocalDate> uniquesFechas = new HashSet<>();
        DateTimeFormatter fechaAnioMesDiaFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (QueryPlanificados obj : lista){
            obj.setHoraPreparacion(parsearHora(obj.getFechaFinPreparacion()));
            obj.setDateFechaPreparacion(parsearFecha(obj.getFechaFinPreparacion()));

            obj.setHoraIniImpresion(parsearHora(obj.getFechaInicioImpresion()));
            obj.setDateFechaIniImpresion(parsearFecha(obj.getFechaInicioImpresion()));

            obj.setHoraFinImpresion(parsearHora(obj.getFechaFinImpresion()));
            obj.setDateFechaFImpresion(parsearFecha(obj.getFechaFinImpresion()));

            uniquesFechas.add(LocalDate.parse(obj.getFechaPlanificada().replaceAll("/","-"), fechaAnioMesDiaFormatter));

        }
        for (LocalDate fecha : uniquesFechas){
            List<QueryPlanificados> objetosMismaFecha = new ArrayList<>();
            for (QueryPlanificados obj : lista){
                LocalDate fechaPlanificada = LocalDate.parse(obj.getFechaPlanificada().replaceAll("/", "-"), fechaAnioMesDiaFormatter);
                if (fecha.isEqual(fechaPlanificada)){
                    objetosMismaFecha.add(obj);
                    System.out.println(obj);
                }
            }

        }

    }

    public LocalTime parsearHora(String hora){
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String[] laHora = hora.split("T");
        return LocalTime.parse(laHora[1], horaFormatter);
    }

    public LocalDate parsearFecha(String fecha){
        DateTimeFormatter fechaAnioMesDiaFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter fechaDiaMesAnioFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String[] laFecha = fecha.split("T");
        return LocalDate.parse(laFecha[0], fechaAnioMesDiaFormatter);
    }


}
