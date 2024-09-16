package com.thinkconstructive.restdemospringweb;
import com.poiji.bind.Poiji;
import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import com.thinkconstructive.restdemospringweb.model.JornadasExcel;
import com.thinkconstructive.restdemospringweb.repository.CloudVendorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class LecturaTurnosExcel {

    @Autowired
    CloudVendorRepository cloudVendorRepository;

    public static void main(String[] args){
        SpringApplication.run(LecturaTurnosExcel.class, args);
    }
    @PostConstruct
    public void init(){
        File archivo = new File("JORNADA REDUCIDA.xlsx");
        List<JornadasExcel> listaTurnos = Poiji.fromExcel(archivo, JornadasExcel.class);
        List<JornadasExcel> jornadasParseadas = new ArrayList<>();
        List<CloudVendor> operarios = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for(JornadasExcel jornada : listaTurnos){
            System.out.println(jornada);
        }
        for(JornadasExcel jornada : listaTurnos){
            if (jornada.getHorario() != null){
                String[] apellido = jornada.getNombre().split(",");
                jornada.setApellido(apellido[0].trim());
                jornada.setHorario(jornada.getHorario().replaceAll(" ",""));

                String[] hora = jornada.getHorario().split("-|/|A");

                try{
                    jornada.setHoraInicio(LocalTime.parse("0"+hora[0], timeFormatter));
                    jornada.setHoraFin(LocalTime.parse(hora[1], timeFormatter));
                    jornadasParseadas.add(jornada);
                }catch (Exception e){
                    System.out.println("Operario que no tiene la hora con el formato establecido -> %s %s".formatted(jornada.getNombre(), jornada.getHorario()));
                }
            }

        }
        for(JornadasExcel jornada : jornadasParseadas){
            try{
                CloudVendor operario = cloudVendorRepository.findUserbySurname(jornada.getApellido());
                if(operario != null){
                    operarios.add(operario);
                    jornada.setId(operario.getId());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        LocalTime seisMañana = LocalTime.of(6, 0);
        LocalTime cuatroTarde = LocalTime.of(16,0);
        LocalTime diezNoche = LocalTime.of(22,0);


        for(CloudVendor operario : operarios){
            for(JornadasExcel jornada : jornadasParseadas){
                if (operario.getId().equals(jornada.getId())){
                    if (jornada.getHoraFin().isBefore(cuatroTarde)){
                        System.out.println("Este operario es de mañana -> %s -- %s".formatted(operario.getNombre(), jornada.getHorario()));
                        operario.setTurno("estatica-M");
                        cloudVendorRepository.save(operario);
                    }else if (jornada.getHoraFin().isBefore(diezNoche)){
                        System.out.println("Este operario es de tarde -> %s -- %s".formatted(operario.getNombre(), jornada.getHorario()));
                        operario.setTurno("estatica-T");
                        cloudVendorRepository.save(operario);
                    }else if (jornada.getHoraFin().isBefore(seisMañana)){
                        System.out.println("Este operario es de noche -> %s -- %s".formatted(operario.getNombre(), jornada.getHorario()));
                        operario.setTurno("estatica-N");
                        cloudVendorRepository.save(operario);
                    }
                }
            }
        }


    }
}
