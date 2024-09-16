package com.thinkconstructive.restdemospringweb;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thinkconstructive.restdemospringweb.model.*;
import com.thinkconstructive.restdemospringweb.repository.CloudVendorRepository;
import com.thinkconstructive.restdemospringweb.repository.FaltasRepository;
import com.thinkconstructive.restdemospringweb.repository.HorarioTrabajadorRepository;
import com.thinkconstructive.restdemospringweb.service.impl.CloudVendorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import com.thinkconstructive.restdemospringweb.service.impl.EventosServiceImpl;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class Consultas {
    @Autowired
    EventosServiceImpl eventosService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CloudVendorServiceImpl cloudVendorService;
    @Autowired
    HorarioTrabajadorRepository horarioTrabajadorRepository;
    @Autowired
    FaltasRepository faltasRepository;
    @Autowired
    CloudVendorRepository cloudVendorRepository;
    public static void main(String[] args){
        SpringApplication.run(Consultas.class, args);
    }
     @PostConstruct
    @Scheduled(cron = "0 30 0 * * *")
    public void init(){
         Pattern patternManana = Pattern.compile("\\b(mañana)\\b", Pattern.CASE_INSENSITIVE);
         Pattern patternTarde = Pattern.compile("\\b(tarde)\\b", Pattern.CASE_INSENSITIVE);
         Pattern patternNoche = Pattern.compile("\\b(noche)\\b", Pattern.CASE_INSENSITIVE);
         Pattern rangoHoras = Pattern.compile("(\\d{1,2}:\\d{2}-\\d{1,2}:\\d{2})");
         DateTimeFormatter timeFormatterDosceros = DateTimeFormatter.ofPattern("HH:mm");
         DateTimeFormatter timeFormatterUncero = DateTimeFormatter.ofPattern("H:mm");
        //SACAMOS PRIMERO LOS USUARIOS EXISTENTES DE NUESTRA BD 
        List<CloudVendor> usuarios = sacarUsuariosGet();
         List<HorarioTrabajador> listaHorasTrabajador = new ArrayList<>();
         Set<CloudVendor> operarioDespedidos = new HashSet<>();
         System.out.println("VERIFICACIÓN PREVIA: SE VA A COMPROBAR QUE LOS USUARIOS SIGAN ACTIVOS EN LA EMPRESA");
         LocalDate today = LocalDate.now();
         LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
         Integer totalUsers = usuarios.size();
        for(CloudVendor user : usuarios){
            listaHorasTrabajador = horarioTrabajadorRepository.findAllById(user.getId());

            if(!listaHorasTrabajador.isEmpty()&&!listaHorasTrabajador.getLast().getFecha().toString().equals(monday.toString())){
                operarioDespedidos.add(user);
            }

            totalUsers--;
            System.out.print("\rUsuarios que faltan por verificar: " + totalUsers);
        }
         System.out.println("");
         System.out.println("TOTAL DE OPERARIOS DESPEDIDOS -> "+operarioDespedidos.size());
         System.out.println("   ");
         System.out.println("Quitando de la BD los usuarios despedidos");

        for(CloudVendor user : operarioDespedidos){
            System.out.println(user);
            deleteOperario(user.getId());
        }

         System.out.println("PROCEDIENDO CON LA ACTUALIZACIÓN DE LOS TURNOS");
        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }

         usuarios = sacarUsuariosGet();

        //DESPUÉS VALIDAMOS QUE LOS EVENTOS DE LA BD TIENE RELACIONADO A LOS MISMO USUARIOS EXISTENTES EN NUESTRA BD
        List<Eventos> listaEventos = new ArrayList<>();
        listaEventos = eventosService.getAllCloudVendors();

         // Obtener la fecha y hora actual
         LocalDateTime now = LocalDateTime.now();
         LocalDate nowDia = LocalDate.now();

         // Formatear la fecha y hora
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         String formattedDateTime = now.format(formatter);
         List<Eventos> eventosNullPointerExceptions = new ArrayList<>();
         List<CloudVendor> operariosSinRegistros = new ArrayList<>();

        if (!listaEventos.isEmpty() && !usuarios.isEmpty()){
            for(Eventos evento : listaEventos){
                evento.setObservaciones(evento.getTurnoActualizado());
            }

            for (CloudVendor operario : usuarios){
                listaHorasTrabajador = horarioTrabajadorRepository.findAllById(operario.getId());
                if(listaHorasTrabajador.size() >= 3){
                    listaHorasTrabajador = listaHorasTrabajador.subList(listaHorasTrabajador.size() - 3, listaHorasTrabajador.size());
                    Integer Manana = 0;
                    Integer Tarde = 0;
                    Integer Noche = 0;
                    
                    String turnoActual = "";
                    if (listaHorasTrabajador.getLast().getRotacion() == 1){
                        for (int i = listaHorasTrabajador.size()-1; i >= 0 ; i--) {
                            switch (listaHorasTrabajador.get(i).getRotacion()){
                                case 1:
                                    Matcher manana = patternManana.matcher(listaHorasTrabajador.get(i).getTurno());
                                    Matcher tarde = patternTarde.matcher(listaHorasTrabajador.get(i).getTurno());
                                    Matcher noche = patternNoche.matcher(listaHorasTrabajador.get(i).getTurno());
                                    if (manana.find()){
                                        Manana += 1;
                                        if (i == 2){
                                            turnoActual = "Mañana";
                                        }
                                    } else if (tarde.find()) {
                                        Tarde += 1;
                                        if (i == 2){
                                            turnoActual = "Tarde";
                                        }
                                    } else if (noche.find()) {
                                        Noche += 1;
                                        if (i == 2){
                                            turnoActual = "Noche";
                                        }
                                    }
                                    break;
                                case 0:
                                    break;
                            }
                        }

                    }else if(listaHorasTrabajador.getLast().getRotacion() == 0){
                        LocalTime cincoManana = LocalTime.of(5, 0);
                        LocalTime cuatroTarde = LocalTime.of(16,0);
                        LocalTime doceManana = LocalTime.of(12, 0);
                        LocalTime nueveTarde = LocalTime.of(21, 0);
                        Matcher manana = patternManana.matcher(listaHorasTrabajador.getLast().getTurno());
                        Matcher tarde = patternTarde.matcher(listaHorasTrabajador.getLast().getTurno());
                        Matcher noche = patternNoche.matcher(listaHorasTrabajador.getLast().getTurno());
                        if (manana.find()){
                            turnoActual = "estatica-M";
                        } else if (tarde.find()) {
                            turnoActual = "estatica-T";
                        } else if (noche.find()) {
                            turnoActual = "estatica-N";
                        } else{
                            Matcher horas_rango = rangoHoras.matcher(listaHorasTrabajador.getLast().getTurno());
                            if (horas_rango.find()){
                                String horas_string = horas_rango.group();
                                LocalTime hora_inicio_dato = null;
                                String[] horas_partido = horas_string.split("-");
                                try{
                                    if(horas_partido[0].length() == 4){
                                        hora_inicio_dato = LocalTime.parse(horas_partido[0].substring(0,4), timeFormatterUncero);
                                    }else if (horas_partido[0].length() == 5){
                                        hora_inicio_dato = LocalTime.parse(horas_partido[0].substring(0,5), timeFormatterDosceros);
                                    }
                                }catch (Exception e){
                                    System.out.printf("ESTE CASO NO ES COMPATIBLE");
                                    System.out.println(e);
                                }
                                if(hora_inicio_dato.isAfter(nueveTarde) && hora_inicio_dato.isBefore(cincoManana)) {
                                    turnoActual = "estatica-N";
                                } else if(hora_inicio_dato.isAfter(cincoManana) && hora_inicio_dato.isBefore(doceManana)) {
                                    turnoActual = "estatica-M";
                                } else if(hora_inicio_dato.isAfter(doceManana) && hora_inicio_dato.isBefore(nueveTarde)){
                                    turnoActual = "estatica-T";
                                }
                            } else {

                                String frase = listaHorasTrabajador.getLast().getTurno().replaceAll(" ","");
                                frase = frase.replaceAll("al|a|hasta","-");

                                horas_rango = rangoHoras.matcher(frase);
                                if (horas_rango.find()) {
                                    String horas_string = horas_rango.group();
                                    LocalTime hora_inicio_dato = null;
                                    String[] horas_partido = horas_string.split("-");
                                    try {
                                        if (horas_partido[0].length() == 4) {
                                            hora_inicio_dato = LocalTime.parse(horas_partido[0].substring(0, 4), timeFormatterUncero);
                                        } else if (horas_partido[0].length() == 5) {
                                            hora_inicio_dato = LocalTime.parse(horas_partido[0].substring(0, 5), timeFormatterDosceros);
                                        }
                                    } catch (Exception e) {
                                        System.out.printf("ESTE CASO NO ES COMPATIBLE");
                                        System.out.println(e);
                                    }
                                    if(hora_inicio_dato.isAfter(nueveTarde) && hora_inicio_dato.isBefore(cincoManana)) {
                                        turnoActual = "estatica-N";
                                    } else if(hora_inicio_dato.isAfter(cincoManana) && hora_inicio_dato.isBefore(doceManana)) {
                                        turnoActual = "estatica-M";
                                    } else if(hora_inicio_dato.isAfter(doceManana) && hora_inicio_dato.isBefore(nueveTarde)){
                                        turnoActual = "estatica-T";
                                    }
                                }
                            }
                        }
                    }
                    List<Integer> turnos = new ArrayList<>();
                    turnos.add(Manana);
                    turnos.add(Tarde);
                    turnos.add(Noche);

                    Boolean caseManana = false;
                    Boolean caseTarde = false;
                    Boolean caseNoche = false;

                    List<Boolean> listaCasos = new ArrayList<>();

                    for (int i = 0; i < turnos.size(); i++) {
                        if (turnos.get(i) > 0){
                            switch (i){
                                case 0:
                                    caseManana = true;
                                    break;
                                case 1:
                                    caseTarde = true;
                                    break;
                                case 2:
                                    caseNoche = true;
                            }
                        }
                    }
                    listaCasos.add(caseManana);
                    listaCasos.add(caseTarde);
                    listaCasos.add(caseNoche);


                    
                    String sinNocheT = "sinNoche-T";
                    String sinNocheM = "sinNoche-M";
                    String sinMañanaN = "sinMañana-N";
                    String sinMañanaT = "sinMañana-T";
                    String sinTardeM = "sinTarde-M";
                    String sinTardeN = "sinTarde-N";
                    String estaticaM = "estatica-M";
                    String estaticaT = "estatica-T";
                    String estaticaN = "estatica-N";

                    if(turnoActual != ""){
                        if (listaCasos.toString().replaceAll(" ","").equals("[true,true,true]")){
                            operario.setTurno(turnoActual.trim());
                        } else if (listaCasos.toString().replaceAll(" ","").equals("[true,true,false]")){
                            if(turnoActual.equals("Mañana")){
                                operario.setTurno(sinNocheM.trim());
                            } else if (turnoActual.equals("Tarde")) {
                                operario.setTurno(sinNocheT.trim());
                            }
                        } else if (listaCasos.toString().replaceAll(" ","").equals("[false,true,true]")){
                            if(turnoActual.equals("Tarde")){
                                operario.setTurno(sinMañanaT.trim());
                            } else if (turnoActual.equals("Noche")) {
                                operario.setTurno(sinMañanaN.trim());
                            }
                        } else if (listaCasos.toString().replaceAll(" ","").equals("[true,false,true]")){
                            if(turnoActual.equals("Mañana")){
                                operario.setTurno(sinTardeM.trim());
                            } else if (turnoActual.equals("Noche")) {
                                operario.setTurno(sinTardeN.trim());
                            }
                        }else if (listaCasos.toString().replaceAll(" ","").equals("[true,false,false]")){
                            operario.setTurno(estaticaM.trim());
                        }else if (listaCasos.toString().replaceAll(" ","").equals("[false,true,false]")){
                            operario.setTurno(estaticaT);
                        }else if (listaCasos.toString().replaceAll(" ","").equals("[false,false,true]")){
                            operario.setTurno(estaticaN.trim());
                        }
                        else if (listaCasos.toString().replaceAll(" ","").equals("[false,false,false]")){
                            if(turnoActual.trim().equals("estatica-M")){
                                operario.setTurno(estaticaM.trim());
                            } else if (turnoActual.trim().equals("estatica-T")) {
                                operario.setTurno(estaticaT.trim());
                            }else if (turnoActual.trim().equals("estatica-N")) {
                                operario.setTurno(estaticaN.trim());
                            }
                        }

                        updateOperario(operario);
                    }else {
                        System.out.printf("EL TURNO ACTUAL SIGUE VACIO %s -> %s%n",operario.getNombre(),listaHorasTrabajador.getLast().getTurno());
                    }

                }else if(listaHorasTrabajador.size() != 0){
                    String turnoActual = "";
                    if (listaHorasTrabajador.getLast().getRotacion() == 1){
                        Matcher manana = patternManana.matcher(listaHorasTrabajador.getLast().getTurno());
                        Matcher tarde = patternTarde.matcher(listaHorasTrabajador.getLast().getTurno());
                        Matcher noche = patternNoche.matcher(listaHorasTrabajador.getLast().getTurno());
                        if (manana.find()){
                            turnoActual = "Mañana";
                        } else if (tarde.find()) {
                            turnoActual = "Tarde";
                        } else if (noche.find()) {
                            turnoActual = "Noche";
                        }

                    }else if(listaHorasTrabajador.getLast().getRotacion() == 0){
                        LocalTime cincoManana = LocalTime.of(5, 0);
                        LocalTime cuatroTarde = LocalTime.of(16,0);
                        LocalTime doceManana = LocalTime.of(12, 0);
                        LocalTime nueveTarde = LocalTime.of(21, 0);
                        // LocalTime diezNoche = LocalTime.of(22,0);
                        Matcher manana = patternManana.matcher(listaHorasTrabajador.getLast().getTurno());
                        Matcher tarde = patternTarde.matcher(listaHorasTrabajador.getLast().getTurno());
                        Matcher noche = patternNoche.matcher(listaHorasTrabajador.getLast().getTurno());
                        if (manana.find()){
                            turnoActual = "estatica-M";
                        } else if (tarde.find()) {
                            turnoActual = "estatica-T";
                        } else if (noche.find()) {
                            turnoActual = "estatica-N";
                        } else{
                            Matcher horas_rango = rangoHoras.matcher(listaHorasTrabajador.getLast().getTurno());
                            if (horas_rango.find()){
                                String horas_string = horas_rango.group();
                                LocalTime hora_inicio_dato = null;
                                String[] horas_partido = horas_string.split("-");
                                try{
                                    if(horas_partido[0].length() == 4){
                                        hora_inicio_dato = LocalTime.parse(horas_partido[0].substring(0,4), timeFormatterUncero);
                                    }else if (horas_partido[0].length() == 5){
                                        hora_inicio_dato = LocalTime.parse(horas_partido[0].substring(0,5), timeFormatterDosceros);
                                    }
                                }catch (Exception e){
                                    System.out.printf("ESTE CASO NO ES COMPATIBLE");
                                    System.out.println(e);
                                }
                                if(hora_inicio_dato.isAfter(nueveTarde) && hora_inicio_dato.isBefore(cincoManana)) {
                                    turnoActual = "estatica-N";
                                } else if(hora_inicio_dato.isAfter(cincoManana) && hora_inicio_dato.isBefore(doceManana)) {
                                    turnoActual = "estatica-M";
                                } else if(hora_inicio_dato.isAfter(doceManana) && hora_inicio_dato.isBefore(nueveTarde)) {
                                    turnoActual = "estatica-T";
                                }
                            } else {

                                String frase = listaHorasTrabajador.getLast().getTurno().replaceAll(" ","");
                                frase = frase.replaceAll("al|a|hasta","-");

                                horas_rango = rangoHoras.matcher(frase);
                                if (horas_rango.find()) {
                                    String horas_string = horas_rango.group();
                                    LocalTime hora_inicio_dato = null;
                                    String[] horas_partido = horas_string.split("-");
                                    try {
                                        if (horas_partido[0].length() == 4) {
                                            hora_inicio_dato = LocalTime.parse(horas_partido[0].substring(0, 4), timeFormatterUncero);
                                        } else if (horas_partido[0].length() == 5) {
                                            hora_inicio_dato = LocalTime.parse(horas_partido[0].substring(0, 5), timeFormatterDosceros);
                                        }
                                    } catch (Exception e) {
                                        System.out.printf("ESTE CASO NO ES COMPATIBLE");
                                        System.out.println(e);
                                    }
                                    if(hora_inicio_dato.isAfter(nueveTarde) && hora_inicio_dato.isBefore(cincoManana)) {
                                        turnoActual = "estatica-N";
                                    } else if(hora_inicio_dato.isAfter(cincoManana) && hora_inicio_dato.isBefore(doceManana)) {
                                        turnoActual = "estatica-M";
                                    } else if(hora_inicio_dato.isAfter(doceManana) && hora_inicio_dato.isBefore(nueveTarde)){
                                        turnoActual = "estatica-T";
                                    }
                                }
                            }
                        }
                    }
                    operario.setTurno(turnoActual.trim());
                    updateOperario(operario);
                }else{
                    System.out.println("ESTE OPERARIO NO TIENE REGISTROS > "+operario);
                    operariosSinRegistros.add(operario);
                }

            }
            System.out.printf("SE HAN ENCONTRADO %s OPERARIOS SIN REGISTROS%n",operariosSinRegistros.size());
            for(CloudVendor operario : operariosSinRegistros){
                System.out.println(operario);
            }
            listaEventos.sort(Comparator.comparing(Eventos::getLafechaInicio));
            List<EventosApi1> listaEventosEnviar = new ArrayList<>();
            List<Faltas> listaFaltasEnviar = new ArrayList<>();

            int contador = 0;
            int fechas_antiguas = 0;
            deleteAll();
            faltasRepository.deleteAll();
            for (CloudVendor user : usuarios) {
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
                            LocalDate fecha = LocalDate.parse(fecha_string, formatterFecha);
                            if (fecha.isBefore(nowDia)){
                                fechas_antiguas ++;
                                System.out.printf("Evento con usuario %s descartado FECHA-FIN {%s}%n", user.getId(),evento.getLafechaFin());
                            }else{
                                System.out.println("DETALLE EVENTO "+newEvento);
                                listaEventosEnviar.add(newEvento);
                            }

                        }
                    }catch (Exception e){
                        System.out.println("NULL POINTER EXCEPTION - > %s -- %s".formatted(evento, user));
                        eventosNullPointerExceptions.add(evento);
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
                            updateOperario(user);
                        }
                    }
                }
            }
            Integer orden_contador = 0;
            for(EventosApi1 evento : listaEventosEnviar){
                evento.setOrden(orden_contador);
                enviarSolicitudPost(evento);
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

//            if(!eventosNullPointerExceptions.isEmpty()){
//                System.out.printf("SE HAN ENCONTRADO  %s EVENTOS NULOS%n",eventosNullPointerExceptions.size());
//                for(Eventos evento : eventosNullPointerExceptions){
//                    System.out.println(evento);
//                }
//            }
        }else{
            System.out.println("NO HAY EVENTOS O USUARIOS EN LA BD");
        }
    }

        private void enviarSolicitudPost(EventosApi1 evento) {
        String url = "http://localhost:8001/eventos"; // URL de la otra API
        try {
            restTemplate.put(url, evento, String.class);
            System.out.println("Solicitud por post (evento): usuario relacionado -> " + evento.getUsuario_id());
        } catch (Exception e) {
            System.out.println("EL LISTENER NO ESTÁ FUNCIONANDO !!!");
            e.printStackTrace();
        }
        }

        private void updateOperario(CloudVendor operario) {
            String url = "http://localhost:8001/cloudvendor"; // URL de la otra API
            try {
                restTemplate.put(url, operario, String.class);
                System.out.println("Solicitud UPDATE enviada del operario -> " + operario.getNombre());
            } catch (Exception e) {
                System.out.println("EL LISTENER NO ESTÁ FUNCIONANDO !!!");
                e.printStackTrace();
            }
        }

    private void deleteOperario(Integer id) {
        String url = "http://localhost:8001/cloudvendor/"+id; // URL de la otra API
        try {
            restTemplate.delete(url, Void.class);
            System.out.println("Solicitud DELETE enviada del operario -> " + id);
        } catch (Exception e) {
            System.out.println("EL LISTENER NO ESTÁ FUNCIONANDO !!!");
            e.printStackTrace();
        }
    }
        private List<CloudVendor> sacarUsuariosGet() {
        String url = "http://localhost:8001/cloudvendor"; // URL de la otra API
        List<CloudVendor> usersList = new ArrayList<>();
        try {
            // Realizar solicitud GET y almacenar la respuesta en un array de usuarios
            CloudVendor[] usuarios = restTemplate.getForObject(url, CloudVendor[].class);
            usersList = Arrays.asList(usuarios);
        } catch (Exception e) {
            System.out.println("EL LISTENER NO ESTÁ FUNCIONANDO !!!");
            e.printStackTrace();
        }
        return usersList;
        }

    private void deleteAll() {
        String url = "http://localhost:8001/eventos/deleteall"; // URL de la otra API
        try {
            // Realizar solicitud GET sin esperar nada a cambio
            restTemplate.getForObject(url, Void.class);
        } catch (Exception e) {
            System.out.println("EL LISTENER NO ESTÁ FUNCIONANDO !!!");
            e.printStackTrace();
        }
    }
}


