package com.thinkconstructive.restdemospringweb;

import com.poiji.bind.Poiji;
import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import com.thinkconstructive.restdemospringweb.model.Objetos;
import com.thinkconstructive.restdemospringweb.model.Operario;
import com.thinkconstructive.restdemospringweb.service.impl.CloudVendorServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.*;


public class LecturaTurnos {
    @Autowired
    CloudVendorServiceImpl operariosService;

    public static void main(String[] args) {
        SpringApplication.run(LecturaTurnos.class, args);
    }

    @PostConstruct
    public void init() {
        File archivo = new File("Marcajes operarios.xlsx");
        List<CloudVendor> operariosBD = operariosService.getAllCloudVendors();
        List<Objetos> operariosExcel = Poiji.fromExcel(archivo, Objetos.class);
        Set<Integer> uniqueIds = new HashSet<>();
        List<Operario> usuarios = new ArrayList<>();
        List<Operario> operariosNoexisten = new ArrayList<>();
        List<Operario> operarioEstaticos = new ArrayList<>();
        for (Objetos obj : operariosExcel) {
            uniqueIds.add(obj.getOperario());
        }
        int contador = 0;
        int operariosContador = 0;
        for (Integer id : uniqueIds) {
            Operario user = new Operario(id);
            usuarios.add(user);
        }
        for (Operario op : usuarios) {
            for (Objetos obj : operariosExcel) {
                if (Objects.equals(op.getId(), obj.getOperario())) {

                    if (obj.getTurno() != null) {
                        switch (obj.getTurno()) {
                            case "M" -> op.setManana(obj.getNumeroOperariones().doubleValue());
                            case "T" -> op.setTarde(obj.getNumeroOperariones().doubleValue());
                            case "N" -> op.setNoche(obj.getNumeroOperariones().doubleValue());
                        }
                    }
                }
            }
//        for (Operario op : usuarios){
//            double manana = 0;
//            double tarde = 0;
//            double noche = 0;
//            for (String turno : op.getListaTurnos()){
//                if (turno != null){
//                    switch (turno) {
//                        case "M" -> manana++;
//                        case "T" -> tarde++;
//                        case "N" -> noche++;
//                    }
//                }
//            }
//
//            manana = Math.round(manana/op.getListaTurnos().size()*100);
//            tarde = Math.round(tarde/op.getListaTurnos().size()*100);
//            noche = Math.round(noche/op.getListaTurnos().size()*100);
//
//            System.out.println("PORCENTAJES MAÑANA->%s TARDE->%s NOCHE->%s".formatted(manana,tarde,noche));
//            boolean existe = false;
//            for(CloudVendor opBD : operariosBD){
//                if(Objects.equals(op.getId(), opBD.getId())){
//                    existe = true;
//                    operariosContador++;
//                    if (manana == 100){
//                        operarioEstaticos.add(op);
//                        System.out.println("operario id:%s-%s es de mañana estatica -> Mañana: %s%% Tarde: %s%% Noche: %s%%".formatted(opBD.getId(),opBD.getNombre(),manana,tarde,noche));
//                    } else if (tarde == 100) {
//                        operarioEstaticos.add(op);
//                        System.out.println("operario id:%s-%s es de tarde estatica -> Mañana: %s%% Tarde: %s%% Noche: %s%%".formatted(opBD.getId(),opBD.getNombre(),manana,tarde,noche));
//                    } else if (noche == 100) {
//                        operarioEstaticos.add(op);
//                        System.out.println("operario id:%s-%s es de noche estatica -> Mañana: %s%% Tarde: %s%% Noche: %s%%".formatted(opBD.getId(),opBD.getNombre(),manana,tarde,noche));
//                    }
//                }
//            }
//            if (!existe){
//                operariosNoexisten.add(op);
//            }
//
//        }
//        System.out.println("CANTIDAD DE OPERARIOS EXISTENTES "+operariosContador);
//        System.out.println("CANTIDAD DE OPERARIOS QUE NO ESTAN EN LA BD "+operariosNoexisten.size());
//        int cosas = 0;
//        System.out.println("ACONTINUACIÓN SE MUESTRAN SOLO 20 DE LOS %s OPERARIOS QUE NON EXISTEN".formatted(operariosNoexisten.size()));
//        for(Operario op:operariosNoexisten){
//            if(op.getId() > 0){
//                System.out.println(op);
//                cosas ++;
//                if (cosas > 20){
//                    break;
//                }
//            }
//        }
//        System.out.println("OPERARIOS CON TURNOS ESTÁTICOS "+operarioEstaticos.size());
//        for(Operario obj : operarioEstaticos){
////            for (CloudVendor opBD : operariosBD){
////                if(Objects.equals(obj.getId(), opBD.getId())){
////                    System.out.println("id: %s, Nombre: %s, Turno(no rota): %s".formatted(opBD.getId(),opBD.getNombre(),obj.getListaTurnos().getFirst()));
////                }
////            }
//            System.out.println(obj);
//        }
        }


        for(Operario op : usuarios){
            op.setPorcentajeManana(op.getManana());
            op.setPorcentajeTarde(op.getTarde());
            op.setPorcentajeNoche(op.getNoche());
            boolean existe = false;
            for (CloudVendor opBD : operariosBD){
                if (Objects.equals(op.getId(), opBD.getId())){
                    existe = true;
                    operariosContador++;
                    if (op.getPorcentajeManana() == 100){
                        operarioEstaticos.add(op);
                        System.out.println("operario id:%s-%s es de mañana estatica -> Mañana: %s%% Tarde: %s%% Noche: %s%%".formatted(opBD.getId(),opBD.getNombre(),op.getPorcentajeManana(),op.getPorcentajeTarde(),op.getPorcentajeNoche()));
                    } else if (op.getPorcentajeTarde() == 100) {
                        operarioEstaticos.add(op);
                        System.out.println("operario id:%s-%s es de tarde estatica -> Mañana: %s%% Tarde: %s%% Noche: %s%%".formatted(opBD.getId(),opBD.getNombre(),op.getPorcentajeManana(),op.getPorcentajeTarde(),op.getPorcentajeNoche()));
                    } else if (op.getPorcentajeNoche() == 100) {
                        operarioEstaticos.add(op);
                        System.out.println("operario id:%s-%s es de noche estatica -> Mañana: %s%% Tarde: %s%% Noche: %s%%".formatted(opBD.getId(),opBD.getNombre(),op.getPorcentajeManana(),op.getPorcentajeTarde(),op.getPorcentajeNoche()));
                    }
                }
            }
            if(!existe){
                System.out.println("OPERARIO NO REGISTRADO EN LA BD -> "+ op.getId());
                operariosNoexisten.add(op);
            }

        }

        System.out.println("OPERARIOS REGISTRADOS: %s - OPERARIOS NO REGISTRADOS: %s".formatted(operariosContador,operariosNoexisten.size()));
        System.out.println("ACONTINUACIÓN SE MUESTRAN SOLO 20 DE LOS %s OPERARIOS QUE NON EXISTEN".formatted(operariosNoexisten.size()));
        int contador_operario_noregistrado = 0;
        for(Operario op:operariosNoexisten){
            if(op.getId() > 0){
                System.out.println(op);
                contador_operario_noregistrado ++;
                if (contador_operario_noregistrado > 20){
                    break;
                }
            }
        }

        System.out.println("OPERARIOS CON TURNOS ESTÁTICOS "+operarioEstaticos.size());
        for(Operario obj : operarioEstaticos){
            for (CloudVendor opBD : operariosBD){
                if(Objects.equals(obj.getId(), opBD.getId())){
                    double auxiliar = 0;
                    String turno = "";
                    List<Double> lista = new ArrayList<>();
                    lista.add(obj.getPorcentajeManana());
                    lista.add(obj.getPorcentajeTarde());
                    lista.add(obj.getPorcentajeNoche());
                    for ( Double value : lista){
                        if(value > auxiliar){
                            auxiliar = value;
                        }
                    }
                    if(auxiliar == obj.getPorcentajeManana()){
                        turno = "Mañana";
                    } else if (auxiliar == obj.getPorcentajeTarde()) {
                        turno = "Tarde";
                    }else if (auxiliar == obj.getPorcentajeNoche()){
                        turno = "Noche";
                    }
                    System.out.println("id: %s, Nombre: %s, Turno(no rota): %s".formatted(opBD.getId(),opBD.getNombre(),turno));
                }
            }
//            System.out.println(obj);
        }

    }
}
