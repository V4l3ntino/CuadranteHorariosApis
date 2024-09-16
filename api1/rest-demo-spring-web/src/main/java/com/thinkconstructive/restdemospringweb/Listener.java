package com.thinkconstructive.restdemospringweb;

import com.thinkconstructive.restdemospringweb.model.*;
import com.thinkconstructive.restdemospringweb.repository.MaquinaActualRepository;
import com.thinkconstructive.restdemospringweb.repository.OperariosFechasRepository;
import com.thinkconstructive.restdemospringweb.repository.OperariosMaquinaRepository;
import com.thinkconstructive.restdemospringweb.repository.PermutasRepository;
import com.thinkconstructive.restdemospringweb.service.impl.CloudVendorServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@EnableScheduling
@SpringBootApplication
public class Listener {

    @Autowired
    CloudVendorServiceImpl cloudVendorService;
    @Autowired
    OperariosFechasRepository operariosFechasRepository;
    @Autowired
    OperariosMaquinaRepository operariosMaquinaRepository;
    @Autowired
    MaquinaActualRepository maquinaActualRepository;
    @Autowired
    PermutasRepository permutasRepository;

    public static void main(String[] args){
        SpringApplication.run(Listener.class, args);
    }
    @Scheduled(cron = "0 28 0 * * *")
    @PostConstruct
    public void init(){
        Random random = new Random();
        List<String> listaTurnos = Arrays.asList("Mañana","Tarde","Noche");




        File archivo = new File("operarios2.xlsx");

        List<OperariosFechas> operario = operariosFechasRepository.findAll();
        List<OperariosMaquina> operariosMaquinaAnio = operariosMaquinaRepository.findAll();
        List<MaquinaActual> maquinaActual = maquinaActualRepository.findAll();
        List<OperariosFechas> listaOperariosNoTienenMaquin = new ArrayList<>();
        List<Permutas> listaPermutas = permutasRepository.findAll();

        Collections.sort(maquinaActual, new Comparator<MaquinaActual>() {
            @Override
            public int compare(MaquinaActual o1, MaquinaActual o2) {
                Date fecha1 = null;
                Date fecha2 = null;
                try {
                    fecha1 = new SimpleDateFormat("yyyy/MM/dd").parse(o1.getFechaTrabajo());
                    fecha2 = new SimpleDateFormat("yyyy/MM/dd").parse(o2.getFechaTrabajo());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return fecha2.compareTo(fecha1);
            }
        });


        Set<OperariosFechas> operariosConMaquina = new HashSet<>();
        for(OperariosFechas op : operario){
            for (OperariosMaquina maquina : operariosMaquinaAnio){
                if(op.getOperario_id().equals(maquina.getOperario_id())){
                    op.setTieneMaquina(true);
                    op.setListaMaquinas(maquina);
                    operariosConMaquina.add(op);
                }else{
                    op.setTieneMaquina(false);
                }
            }
        }
        List<String> maquinasNombre = new ArrayList<>();
        maquinasNombre.add("autos");
        maquinasNombre.add("bordado");
        maquinasNombre.add("cosido");
        maquinasNombre.add("digital");
        maquinasNombre.add("horno");
        maquinasNombre.add("laser");
        maquinasNombre.add("otros");
        maquinasNombre.add("planchas");
        maquinasNombre.add("sublimacion");
        maquinasNombre.add("pulpos");
        maquinasNombre.add("tampo");
        maquinasNombre.add("termo");


        for(OperariosFechas op : operariosConMaquina){
            List<Integer> numeroOperacionesPorMaquina = new ArrayList<>();
            Integer autos = 0;
            Integer bordado = 0;
            Integer cosido = 0;
            Integer digital = 0;
            Integer horno = 0;
            Integer laser = 0;
            Integer otros = 0;
            Integer planchas = 0;
            Integer sublimacion = 0;
            Integer pulpos = 0;
            Integer tampo = 0;
            Integer termo = 0;
            for (OperariosMaquina maquina : op.getListaMaquinas()){
                for (int i = 0; i < maquina.getAllCantidades().size(); i++) {
                    switch (i){
                        case 0:
                            if (maquina.getAllCantidades().get(i) != null){
                                autos += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 1:
                            if (maquina.getAllCantidades().get(i) != null){
                                bordado += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 2:
                            if (maquina.getAllCantidades().get(i) != null){
                                cosido += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 3:
                            if (maquina.getAllCantidades().get(i) != null){
                                digital += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 4:
                            if (maquina.getAllCantidades().get(i) != null){
                                horno += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 5:
                            if (maquina.getAllCantidades().get(i) != null){
                                laser += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 6:
                            if (maquina.getAllCantidades().get(i) != null){
                                otros += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 7:
                            if (maquina.getAllCantidades().get(i) != null){
                                planchas += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 8:
                            if (maquina.getAllCantidades().get(i) != null){
                                sublimacion += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 9:
                            if (maquina.getAllCantidades().get(i) != null){
                                pulpos += maquina.getAllCantidades().get(i);
                                break;
                            }
                        case 10:
                            if (maquina.getAllCantidades().get(i) != null){
                                tampo += maquina.getAllCantidades().get(i);
                                break;
                            }

                        case 11:
                            if (maquina.getAllCantidades().get(i) != null){
                                termo += maquina.getAllCantidades().get(i);
                                break;
                            }

                    }
                }
            }
            numeroOperacionesPorMaquina.add(autos);
            numeroOperacionesPorMaquina.add(bordado);
            numeroOperacionesPorMaquina.add(cosido);
            numeroOperacionesPorMaquina.add(digital);
            numeroOperacionesPorMaquina.add(horno);
            numeroOperacionesPorMaquina.add(laser);
            numeroOperacionesPorMaquina.add(otros);
            numeroOperacionesPorMaquina.add(planchas);
            numeroOperacionesPorMaquina.add(sublimacion);
            numeroOperacionesPorMaquina.add(pulpos);
            numeroOperacionesPorMaquina.add(tampo);
            numeroOperacionesPorMaquina.add(termo);

            Integer auxiliar = 0;
            Integer auxiliarIndex = 0;
            Integer auxiliar2 = 0;
            Integer auxiliar2Index = 0;
            Integer contador = 0;
            Integer contadorCeros = 0;
            for (Integer numero : numeroOperacionesPorMaquina){
                System.out.println(numeroOperacionesPorMaquina);
                if(numero > auxiliar){
                    auxiliar = numero;
                    auxiliarIndex = contador;
                } else if (numero == 0){
                    contadorCeros++;
                }
                contador ++;
            }
            contador = 0;
            for (Integer numero : numeroOperacionesPorMaquina){
                if (numero < auxiliar && numero > auxiliar2){
                    auxiliar2 = numero;
                    auxiliar2Index = contador;
                }
                contador ++;
            }
            String maquina = maquinasNombre.get(auxiliarIndex);
            String segundoConocimiento = maquinasNombre.get(auxiliar2Index);
            if (contadorCeros.equals(11)){
                segundoConocimiento = "No tiene";
            }
            op.setConocimiento(segundoConocimiento);
            op.setMaquina(maquina);
            List<MaquinaActual> listaMaquinasOperario = new ArrayList<>();
            for(MaquinaActual machine : maquinaActual){
                if (machine.getIdOperario().equals(op.getOperario_id())){
                    listaMaquinasOperario.add(machine);
                }
            }
            String maquinaOperarioActual = "";
            if(listaMaquinasOperario.isEmpty()){
                op.setMaquina("No tiene");
                listaOperariosNoTienenMaquin.add(op);
            }else{
                maquinaOperarioActual = listaMaquinasOperario.getFirst().getMaquina();
                maquinaOperarioActual = switch (maquinaOperarioActual) {
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
                op.setConocimiento(maquina);
                op.setMaquina(maquinaOperarioActual);
            }
            System.out.println("LA MAQUINA ACTUAL DEL OPERARIO %s ES %s ".formatted(op.getOperario_id(),maquinaOperarioActual));



            int randomNumber = random.nextInt(3);
            String[] fechaalta = op.getFechaAlta().split(" |T");
            String[] fechabaja = op.getFechaBaja().split(" |T");
            CloudVendor nuevoOperario = new CloudVendor(op.getOperario_id(),op.getNombre(),op.getMaquina(),listaTurnos.get(randomNumber),op.getTipoOperario(),op.getConocimiento(),invertirFecha(fechaalta[0]),invertirFecha(fechabaja[0]));
            if(!listaPermutas.isEmpty()){
                for (Permutas permuta : listaPermutas){
                    if (permuta.getUsuario_id().equals(nuevoOperario.getId())){
                        nuevoOperario.setPermutado(true);
                    }
                }
            }
            cloudVendorService.createCloudVendor(nuevoOperario);
            System.out.printf("Operario creado en la bd -> {%s}%n",nuevoOperario);
        }
        System.out.println("%s han sido guardados en la BD".formatted(operariosConMaquina.size()));
        System.out.println("%s NO TIENEN MAQUINA".formatted(listaOperariosNoTienenMaquin.size()));


    }
    public String invertirFecha(String fecha){
        String[] parte = fecha.split("-");
        String dia = parte[2];
        String mes = parte[1];
        String anio = parte[0];
        String fechaCompuesta = dia+"-"+mes+"-"+anio;
        return fechaCompuesta;
    }
    
}
