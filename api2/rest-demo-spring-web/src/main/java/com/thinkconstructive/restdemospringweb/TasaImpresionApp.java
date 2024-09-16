package com.thinkconstructive.restdemospringweb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.poiji.bind.Poiji;
import com.thinkconstructive.restdemospringweb.model.CodigoArticulo;
import com.thinkconstructive.restdemospringweb.model.TasaImpresion;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;


public class TasaImpresionApp {

    public static void main (String[] args){
        SpringApplication.run(TasaImpresionApp.class, args);
    }

    @PostConstruct
    public void init(){
        File file = new File("TasasImpresion.xlsx");
        File tasaTeorica = new File("Tasas de operaciones AUTOS.xlsx");
        List<CodigoArticulo> tasaTeoricaArticulo = Poiji.fromExcel(tasaTeorica,CodigoArticulo.class);
        List<TasaImpresion> lista = Poiji.fromExcel(file, TasaImpresion.class);
        List<TasaImpresion> listaFiltered = new ArrayList<>();
        List<TasaImpresion> excepcion = new ArrayList<>();
        Set<Integer> uniqueIds = new HashSet<>();
        for(TasaImpresion obj : lista){

            if (obj.getUnidadesFabricadas() >= 20){
                obj.setSegundosporUnidad();
                if (redondeo(obj.getDuracionHoras()).equals(0.0)){
                    obj.setExcepcion(true);
                    excepcion.add(obj);
                }else{
                    uniqueIds.add(obj.getCodigoArticulo());
                    listaFiltered.add(obj);
                }
            }else{
                obj.setExcepcion(true);
                excepcion.add(obj);
            }
        }

        System.out.println("Aquí se muestran las tasas medias de Segundos por unidad de cada artículo");
        for(Integer id : uniqueIds){
            List<Double> segundosPorUnidad = new ArrayList<>();
            List<TasaImpresion> objeto = new ArrayList<>();
            for(TasaImpresion obj : listaFiltered){
                if (id.equals(obj.getCodigoArticulo())){
                    segundosPorUnidad.add(obj.getSegundosporUnidad());
                    objeto.add(obj);
                }
            }
            Double tasaMedia = 0.0;
            for (Double tasa : segundosPorUnidad){
                tasaMedia += tasa;
            }
            tasaMedia = tasaMedia / segundosPorUnidad.size();

//            System.out.println("Artículo: %s -- TasaMedia: {%s} || SegundosPorUnidad obtenidos -> %s".formatted(id,tasaMedia,segundosPorUnidad.toString()));
            for(CodigoArticulo articulo : tasaTeoricaArticulo){
                if(articulo.getCodigoArticulo().equals(id)){
                    articulo.setTasaMedia(tasaMedia);
                    articulo.setListaUnidades(segundosPorUnidad);
                    Integer unidadesHora = (int) Math.round(3600 / tasaMedia);
                    articulo.setUnidadesHoraReal(unidadesHora);
                    System.out.println(articulo);
                }
            }
        }

        try (PrintWriter documento = new PrintWriter(new File("json/tasaMedia.json"))) {
            JsonArray listaObjetos = new JsonArray();
            for (CodigoArticulo articulo : tasaTeoricaArticulo) {
                Boolean aux = false;
                for (TasaImpresion obj : listaFiltered){
                    if (obj.getCodigoArticulo().equals(articulo.getCodigoArticulo())){
                        aux = true;
                    }
                }
                if (aux){
                    Integer codigoArticulo = articulo.getCodigoArticulo();
                    Integer unidadesHoraTeorica = articulo.getUnidadesHoraTeorica();
                    Double tasaMedia = articulo.getTasaMedia();
                    String listaUnidades = articulo.getListaUnidades().toString();
                    Integer unidadesHoraReal = articulo.getUnidadesHoraReal();
                    Integer diferenciaUnidades = 0;
                    JsonObject objeto = new JsonObject();

                    if(unidadesHoraReal > unidadesHoraTeorica){
                        diferenciaUnidades = unidadesHoraReal - unidadesHoraTeorica;
                    }else{
                        diferenciaUnidades = unidadesHoraTeorica - unidadesHoraReal;
                    }


                    objeto.addProperty("codigoArticulo", codigoArticulo);
                    objeto.addProperty("tasaMedia", tasaMedia);
                    objeto.addProperty("listaDeCasos", listaUnidades);
                    objeto.addProperty("cantidadDesCasos", articulo.getListaUnidades().size());
                    objeto.addProperty("unidadesHoraTeorica", unidadesHoraTeorica);
                    objeto.addProperty("unidadesHoraReal", unidadesHoraReal);
                    objeto.addProperty("diferenciaUnidades",diferenciaUnidades);
                    if (unidadesHoraTeorica != 0) {
                        if (unidadesHoraTeorica > diferenciaUnidades){
                            objeto.addProperty("porcentajeDiferencia", (diferenciaUnidades * 100) / unidadesHoraTeorica);
                        }else{
                            objeto.addProperty("porcentajeDiferencia", "Mas del 100%");
                        }
                    } else {
                        System.out.println("Este articulo tiene unidadeshoraTeorica a cero-> "+articulo.getCodigoArticulo()+" - "+articulo.getUnidadesHoraTeorica());
                        objeto.addProperty("porcentajeDiferencia", "Mas del 100%");
                    }
                    listaObjetos.add(objeto);
                }
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonStringPersonas = gson.toJson(listaObjetos);
            documento.write(jsonStringPersonas);
        } catch (Exception e) {
            System.out.println(e);
        }

        try (PrintWriter documento = new PrintWriter(new File("json/CalculoOperacion.json"))) {
            JsonArray listaObjetos = new JsonArray();
            for (TasaImpresion impreso : listaFiltered) {
                for (CodigoArticulo articulo : tasaTeoricaArticulo){
                    if (impreso.getCodigoArticulo().equals(articulo.getCodigoArticulo())){
                        Integer idPedido = impreso.getIdPedido();
                        String maquina = impreso.getMaquina();
                        Integer unidadesFabricadas = impreso.getUnidadesFabricadas();
                        Double duracionHoras = impreso.getDuracionHoras();
                        Integer codigoArticulo = impreso.getCodigoArticulo();
                        Double segundosporUnidad = impreso.getSegundosporUnidad();
                        Double unidadesporMinuto = impreso.getUnidadesporMinuto();
                        Double unidadesporHora = impreso.getUnidadesporHora();
                        Boolean exception = impreso.getExcepcion();
                        Boolean malaOperacion = impreso.getMalaOperacion();
                        String Calculo = ("comprobacion=Dato real: "+redondeo(duracionHoras)+" Dato obtenido: "+redondeo(((segundosporUnidad * unidadesFabricadas)/ (double) 60)/ (double) 60));
                        JsonObject objeto = new JsonObject();


                        objeto.addProperty("idPedido", idPedido);
                        objeto.addProperty("maquina", maquina);
                        objeto.addProperty("codigoArticulo", codigoArticulo);
                        objeto.addProperty("unidadesFabricadas", unidadesFabricadas);
                        objeto.addProperty("duracionHoras", duracionHoras);
                        objeto.addProperty("segundosporUnidad", segundosporUnidad);
                        objeto.addProperty("unidadesporMinuto",unidadesporMinuto);
                        objeto.addProperty("unidadesporHora",unidadesporHora);
                        objeto.addProperty("exception",exception);
                        objeto.addProperty("malaOperacion",malaOperacion);
                        objeto.addProperty("Calculo",Calculo);

                        listaObjetos.add(objeto);
                    }
                }
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonStringPersonas = gson.toJson(listaObjetos);
            documento.write(jsonStringPersonas);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public Double redondeo(Double numero){
        return Math.round(numero * 10) / 10.0;
    }
}
