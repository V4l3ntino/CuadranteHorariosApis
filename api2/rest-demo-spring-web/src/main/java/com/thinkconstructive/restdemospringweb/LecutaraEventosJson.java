package com.thinkconstructive.restdemospringweb;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import com.thinkconstructive.restdemospringweb.model.EventosJson;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LecutaraEventosJson {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(LecutaraEventosJson.class, args);
    }

    @PostConstruct
    public void init() {
        File input = new File("C:\\Users\\varmido\\Desktop\\vacacionesHumanos.json");
        List<EventosJson> listaEventos = new ArrayList<>();
        try {
            JsonElement elemento = JsonParser.parseReader(new FileReader(input));
            JsonArray listaObjetos = elemento.getAsJsonArray();
            for (JsonElement element : listaObjetos) {
                JsonObject objeto = element.getAsJsonObject();
                Integer trabajador_id = objeto.get("trabajador_id").getAsInt();
                String fecha_inicio = objeto.get("fecha_inicio").getAsString();
                String fecha_fin = objeto.get("fecha_fin").getAsString();
                String nombre = objeto.get("nombre").getAsString();

                EventosJson evento = new EventosJson(trabajador_id, fecha_inicio.substring(0,10), fecha_fin.substring(0,10), nombre, nombre);
                System.out.println(evento);
                listaEventos.add(evento);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // SACAMOS PRIMERO LOS USUARIOS EXISTENTES DE NUESTRA BD
        List<CloudVendor> usuarios = sacarUsuariosGet();

        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDia = LocalDate.now();

        // Formatear la fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = now.format(formatter);

        if (!usuarios.isEmpty()) {
            // Ajustar los turnos actualizados en los eventos
            for (EventosJson evento : listaEventos) {
                evento.setObservaciones(evento.getTurnoActualizado());
                if ("Vacaciones".equals(evento.getTurnoActualizado())) {
                    evento.setTurnoActualizado("V");
                } else {
                    evento.setTurnoActualizado("L");
                }
            }

            int contador = 0;
            int fechas_antiguas = 0;
            deleteAll();

            for (CloudVendor user : usuarios) {
                for (EventosJson evento : listaEventos) {
                    if (evento.getId_evento().equals(user.getId())) {
                        try {
                            LocalDate fecha = LocalDate.parse(evento.getLafechaFin(), formatterFecha);
                            if (fecha.isBefore(nowDia)) {
                                fechas_antiguas++;
                                System.out.printf("Evento con usuario %s descartado FECHA-FIN {%s}%n", user.getId(), evento.getLafechaFin());
                            } else {
                                enviarSolicitudPost(evento);
                            }
                        } catch (Exception e) {
                            System.out.printf("Error al parsear la fecha para el evento del usuario %s: %s%n", user.getId(), e.getMessage());
                        }
                    }
                }
                contador++;
                System.out.printf("Quedan %s usuarios%n", usuarios.size() - contador);
            }
            System.out.println("Cantidad de eventos que han sido descartados -> " + fechas_antiguas);
            System.out.printf("LA BASE DE DATOS HA SIDO ACTUALIZADA CORRECTAMENTE A LAS %s%n", formattedDateTime);
        } else {
            System.out.println("NO HAY EVENTOS O USUARIOS EN LA BD");
        }
    }

    private void enviarSolicitudPost(EventosJson evento) {
        String url = "http://localhost:8001/eventos"; // URL de la otra API
        try {
            restTemplate.put(url, evento, String.class);
            System.out.println("Solicitud por post (evento): usuario relacionado -> " + evento.getId_evento());
        } catch (Exception e) {
            System.out.println("EL LISTENER NO ESTÁ FUNCIONANDO !!!");
            e.printStackTrace();
        }
    }

    private List<CloudVendor> sacarUsuariosGet() {
        String url = "http://localhost:8001/cloudvendor"; // URL de la otra API
        List<CloudVendor> usersList = new ArrayList<>();
        try {
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
            restTemplate.getForObject(url, Void.class);
        } catch (Exception e) {
            System.out.println("EL LISTENER NO ESTÁ FUNCIONANDO !!!");
            e.printStackTrace();
        }
    }
}
