package com.thinkconstructive.restdemospringweb;

import com.thinkconstructive.restdemospringweb.model.*;
import com.thinkconstructive.restdemospringweb.repository.*;
import com.thinkconstructive.restdemospringweb.service.impl.EventosApi1Service;
import jakarta.annotation.PostConstruct;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class Listener {

    @Autowired
    CloudVendorRepository cloudVendorRepository;
    @Autowired
    PicajeRepository picajeRepository;
    @Autowired
    EventosRepository eventosRepository;
    @Autowired
    EventosApi1Repository eventosApi1Repository;
    @Autowired
    FaltasRepository faltasRepository;
    @Autowired
    EventosApi1Service eventosApi1Service;


    public static void main(String[] args){
        SpringApplication.run(Listener.class, args);
    }
    @Scheduled(cron = "0 */5 * * * *")
    @PostConstruct
    public void init(){
        List<CloudVendor> usuarios = cloudVendorRepository.findAll();
        List<Picaje> listaPicajes = picajeRepository.findAll();
        List<Eventos> listaEventos = eventosRepository.findAll();

        listaPicajes.sort(Comparator.comparing(Picaje::getFechaInicio));

        List<CloudVendor> operarioNopican = new ArrayList<>();

        LocalTime cincoManana = LocalTime.of(5, 0);
        LocalTime cuatroTarde = LocalTime.of(16,0);
        LocalTime doceManana = LocalTime.of(12, 0);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate diaActual = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();



        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        int count = 0;
        for(CloudVendor operario : usuarios){
            List<Picaje> listaPicajesOperario = new ArrayList<>();
            for(Picaje pica : listaPicajes){
                if (pica.getOperario().equals(operario.getId())){
                    listaPicajesOperario.add(pica);
                }
            }
            if(listaPicajesOperario.size()>0){
                List<Picaje> listaPicasActuales = new ArrayList<>();
                List<Picaje> listaPicajesDiaAnterior = new ArrayList<>();
                for (Picaje pica : listaPicajesOperario){
                    LocalDate fecha = LocalDate.parse(pica.getFechaInicio().split(" ")[0], dateformatter);
                    if (fecha.equals(diaActual)){
                        listaPicasActuales.add(pica);
                    }
                    if (fecha.equals(diaActual.minusDays(1))){
                        listaPicajesDiaAnterior.add(pica);
                    }
                }

                if(!listaPicasActuales.isEmpty()){

                    String FechaEntrada = listaPicasActuales.getFirst().getFechaInicio();
                    LocalTime hora = LocalTime.parse((FechaEntrada.split(" ")[1]), timeFormatter);
                    if(hora.isAfter(LocalTime.MIDNIGHT) && hora.isBefore(cincoManana)) {
                        System.out.println("%s-%s > TurnoREAL:%s - %s | TurnoSupuesto:%s".formatted(operario.getId(),operario.getNombre(),"NOCHE",FechaEntrada,operario.getTurno()));
                        if(operario.getTurno().equals("Mañana")||operario.getTurno().equals("Tarde")||operario.getTurno().equals("sinNoche-M")||operario.getTurno().equals("sinNoche-T")){
                            operario.setTurno("Noche");
                        } else if (operario.getTurno().equals("sinMañana-T")) {
                            operario.setTurno("sinMañana-N");
                        } else if (operario.getTurno().equals("sinTarde-M")) {
                            operario.setTurno("sinTarde-N");
                        }
                    } else if(hora.isAfter(cincoManana) && hora.isBefore(doceManana)) {
                        System.out.println("%s-%s > TurnoREAL:%s - %s | TurnoSupuesto:%s".formatted(operario.getId(),operario.getNombre(),"MAÑANA",FechaEntrada,operario.getTurno()));
                        if(operario.getTurno().equals("Tarde")||operario.getTurno().equals("Noche")||operario.getTurno().equals("sinMañana-T")||operario.getTurno().equals("sinMañana-N")){
                            operario.setTurno("Mañana");
                        } else if (operario.getTurno().equals("sinTarde-N")) {
                            operario.setTurno("sinTarde-M");
                        } else if (operario.getTurno().equals("sinNoche-T")) {
                            operario.setTurno("sinNoche-M");
                        }
                    } else {
                        System.out.println("%s-%s > TurnoREAL:%s - %s | TurnoSupuesto:%s".formatted(operario.getId(),operario.getNombre(),"TARDE",FechaEntrada,operario.getTurno()));
                        if(operario.getTurno().equals("Mañana")||operario.getTurno().equals("Noche")||operario.getTurno().equals("sinTarde-M")||operario.getTurno().equals("sinTarde-N")){
                            operario.setTurno("Tarde");
                        } else if (operario.getTurno().equals("sinMañana-N")) {
                            operario.setTurno("sinMañana-T");
                        } else if (operario.getTurno().equals("sinNoche-M")) {
                            operario.setTurno("sinNoche-T");
                        }
                    }
                    cloudVendorRepository.save(operario);
                }

                String MaquinaActual = listaPicajesOperario.getLast().getMaquina();
                MaquinaActual = switch(MaquinaActual) {
                    case "SERA" -> "autos";
                    case "BORD" -> "bordado";
                    case "DIG" -> "digital";
                    case "HORNO" -> "horno";
                    case "LASER" -> "laser";
                    case "OTROS" -> "otros";
                    case "PLANCH" -> "planchas";
                    case "SUBLICIN" -> "sublimacion";
                    case "SER" -> "pulpos";
                    case "TAMP" -> "tampo";
                    case "TERMO" -> "termo";
                    default -> "No tiene";
                };




                if(!Objects.equals(MaquinaActual, operario.getMaquina())){
                    operario.setMaquina(MaquinaActual);
                    cloudVendorRepository.save(operario);
                    System.out.println("Operario: %s-%s maquina actualizada".formatted(operario.getId(),operario.getNombre()));
                    count ++;
                }
            }else{
                operarioNopican.add(operario);
            }

        }
        System.out.println(count+" Operarios han sido actualizados");
        String formattedDateTime = now.format(formatter);

        if (!listaEventos.isEmpty() && !usuarios.isEmpty()){
            for(Eventos evento : listaEventos){
                evento.setObservaciones(evento.getTurnoActualizado());
            }

            listaEventos.sort(Comparator.comparing(Eventos::getLafechaInicio));
            List<EventosApi1> listaEventosEnviar = new ArrayList<>();
            List<Faltas> listaFaltasEnviar = new ArrayList<>();

            int contador = 0;
            int fechas_antiguas = 0;
            eventosApi1Service.deleteAll();
            faltasRepository.deleteAll();
            for (CloudVendor user : usuarios) {
                user.setFaltas(0);
                for (Eventos evento : listaEventos){
                    try{
                        if (evento.getEmpresa_codigo_terminal().equals(user.getId())){
                            String string_fecha_inicio = evento.getLafechaInicio().toString();
                            String string_fecha_fin = evento.getLafechaFin().toString();
                            EventosApi1 newEvento = new EventosApi1(evento.getId(),evento.getEmpresa_codigo_terminal(),string_fecha_inicio, string_fecha_fin,evento.getTurnoActualizado(),evento.getObservaciones(),0);
                            if (evento.getTurnoActualizado().equals("Vacaciones")){
                                newEvento.setTurnoActualizado("V");
                            } else if (evento.getTurnoActualizado().equals("Baja Medica EC") || evento.getTurnoActualizado().equals("Baja Medica AT")) {
                                newEvento.setTurnoActualizado("B");
                            } else{
                                newEvento.setTurnoActualizado("L");
                            }
                            if (evento.getTurnoActualizado().trim().equals("Falta al trabajo injustificada") || evento.getTurnoActualizado().trim().equals("Ausencia injustificada con trabajo sin retorno")){
                                Faltas falta = new Faltas(evento.getId(),evento.getEmpresa_codigo_terminal(),string_fecha_inicio, string_fecha_fin,"F.I",evento.getObservaciones(),0);
                                long fechaIni = evento.getLafechaInicio().getTime();
                                long fechaFin = evento.getLafechaFin().getTime();
                                long difference = fechaFin - fechaIni;
                                long diferencia = (difference / (1000 * 60 * 60 * 24));
                                if (diferencia > 0){
                                    for (int i = 0; i <= diferencia; i++) {
                                        user.setFaltas((user.getFaltas()+1));
                                    }
                                }else{
                                    user.setFaltas((user.getFaltas()+1));
                                }
                                cloudVendorRepository.save(user);
                                listaFaltasEnviar.add(falta);

                            }
                            String fecha_string = evento.getLafechaFin().toString();
                            LocalDate fecha = LocalDate.parse(fecha_string, dateformatter);
                            if (fecha.isBefore(diaActual)){
                                fechas_antiguas ++;
                                System.out.printf("Evento con usuario %s descartado FECHA-FIN {%s}%n", user.getId(),evento.getLafechaFin());
                            }else{
                                System.out.println("DETALLE EVENTO "+newEvento);
                                listaEventosEnviar.add(newEvento);
                            }

                        }
                    }catch (Exception e){
                        System.out.println("NULL POINTER EXCEPTION - > %s -- %s".formatted(evento, user));
                    }
                }
                contador ++;
                System.out.printf("Quedan %s usuarios%n", usuarios.size() - contador);
            }
            listaEventosEnviar.sort(Comparator.comparing(EventosApi1::getLafechaInicio));
            listaFaltasEnviar.sort(Comparator.comparing(Faltas::getLafechaInicio));
            DateTimeFormatter formatterFechas = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for(EventosApi1 evento : listaEventosEnviar){
                for(CloudVendor user : usuarios){
                    if(evento.getUsuario_id().equals(user.getId())){
                        LocalDate fechaInicio = LocalDate.parse(evento.getLafechaInicio(), formatterFechas);
                        if(fechaInicio.isBefore(LocalDate.now().minusWeeks(3))){
                            user.setMaquina("INACTIVO");
                            cloudVendorRepository.save(user);
                        }
                    }
                }
            }
            Integer orden_contador = 0;
            for(EventosApi1 evento : listaEventosEnviar){
                evento.setOrden(orden_contador);
                eventosApi1Repository.save(evento);
                System.out.println("EVENTO ENVIADO -> "+evento);
                orden_contador += 1;
            }for(Faltas falta : listaFaltasEnviar){
                falta.setOrden(orden_contador);
                faltasRepository.save(falta);
                System.out.println("FALTA ENVIADA -> "+falta);
                orden_contador += 1;
            }
            System.out.println("Cantidad de eventos que han sido descartados -> "+fechas_antiguas);
            System.out.printf("LA BASE DE DATOS HA SIDO ACTUALIZADA CORRECTAMENTE A LAS %s%n", formattedDateTime);

        }else{
            System.out.println("NO HAY EVENTOS O USUARIOS EN LA BD");
        }

    }

}
