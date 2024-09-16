package com.thinkconstructive.restdemospringweb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.poiji.bind.Poiji;
import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import com.thinkconstructive.restdemospringweb.model.Eventos;
import com.thinkconstructive.restdemospringweb.model.EventosExcel;
import com.thinkconstructive.restdemospringweb.repository.CloudVendorRepository;
import com.thinkconstructive.restdemospringweb.repository.EventosRepository;
import com.thinkconstructive.restdemospringweb.service.impl.EventosServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class LecturaEventosExcel {
    @Autowired
    CloudVendorRepository cloudVendorRepository;
    @Autowired
    EventosServiceImpl eventosService;
    public static void main(String[] args){
        SpringApplication.run(LecturaEventosExcel.class, args);
    }
    @PostConstruct
    public void init(){
        File archivo = new File("modelo_importacion_vacaciones.xlsx");

        List<EventosExcel> eventos = Poiji.fromExcel(archivo, EventosExcel.class);

        List<CloudVendor> operariosConEventos = new ArrayList<>();
        for(EventosExcel evento : eventos){
            String[] apellido = evento.getNombre().split(",");
            evento.setApellido(apellido[0].trim());

            String fechaInicio = "";
            String fechaFin = "";
            String dia = "";
            String mes = "";
            String anio = "";

            dia = evento.getFechaInicio().substring(0,2);
            mes = evento.getFechaInicio().substring(3,5);
            anio = evento.getFechaInicio().substring(6,10);

            fechaInicio = "%s-%s-%s".formatted(anio,mes,dia);

            dia = evento.getFechaFin().substring(0,2);
            mes = evento.getFechaFin().substring(3,5);
            anio = evento.getFechaFin().substring(6,10);

            fechaFin = "%s-%s-%s".formatted(anio,mes,dia);

            evento.setFechaInicio(fechaInicio);
            evento.setFechaFin(fechaFin);
//
//            evento.setFechaInicio(evento.getFechaInicio().replaceAll("/","-"));
//            evento.setFechaFin(evento.getFechaFin().replaceAll("/","-"));
            try{
                CloudVendor operario = cloudVendorRepository.findUserbySurname(evento.getApellido());
                if(operario != null){
                    operariosConEventos.add(operario);
                    evento.setCodigoTrabajador(operario.getId());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        eventosService.deleteAll();
        for(CloudVendor op : operariosConEventos){
            if (op != null){
                op.setEvento(true);
                cloudVendorRepository.save(op);
                for ( EventosExcel licencia : eventos){
                    if(licencia.getCodigoTrabajador().equals(op.getId())){
                        Eventos evento = new Eventos(op.getId(),licencia.getFechaInicio(), licencia.getFechaFin(), licencia.getTurno(),"",0);
                        eventosService.createCloudVendor(evento);
                        System.out.printf("SE HA GUARDADO CORRECTAMENTE EL EVENTO DEL OPERARIO %s -> %s%n", op.getId(),op.getNombre());
                    }
                }
            }
        }

        //ESCRITURA DE DATOS EN JSON

        try (PrintWriter documento = new PrintWriter(new File("json/operariosEvento.json"))) {
            JsonArray listaObjetos = new JsonArray();
            for (CloudVendor operario : operariosConEventos) {

                Integer id = operario.getId();
                String nombre = operario.getNombre();

                JsonObject objeto = new JsonObject();
                objeto.addProperty("Nombre", nombre);
                objeto.addProperty("id", id);
                listaObjetos.add(objeto);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonStringPersonas = gson.toJson(listaObjetos);
            documento.write(jsonStringPersonas);
            System.out.println("SE HA GENERADO EL JSON");
        } catch (Exception e) {
            e.printStackTrace();
        }

//----------------------------------------------------------------------------------



    }
}

