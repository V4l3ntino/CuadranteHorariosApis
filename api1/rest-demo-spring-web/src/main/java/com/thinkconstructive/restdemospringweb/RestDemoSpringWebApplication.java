package com.thinkconstructive.restdemospringweb;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.poiji.bind.Poiji;
import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import com.thinkconstructive.restdemospringweb.model.Eventos;
import com.thinkconstructive.restdemospringweb.service.impl.CloudVendorServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class RestDemoSpringWebApplication {
	@Autowired
	CloudVendorServiceImpl cloudVendorService;
	public static void main(String[] args) {
		SpringApplication.run(RestDemoSpringWebApplication.class, args);
	}
	@PostConstruct
	public void init() {

//		//LECTURA DE UN JSON CON EVENTOS
//		File input = new File("C:\\Users\\varmido\\Desktop\\vacacionesHumanos.json");
//		ArrayList<Eventos> listaEventos = new ArrayList<>();
//		try {
//			JsonElement elemento = JsonParser.parseReader(new FileReader(input));
//			JsonArray listaObjetos = elemento.getAsJsonArray();
//			for (JsonElement element : listaObjetos) {
//				JsonObject objeto = element.getAsJsonObject();
//				Integer trabajador_id = objeto.get("trabajador_id").getAsInt();
//				String fecha_inicio = objeto.get("fecha_inicio").getAsString();
//				String fecha_fin = objeto.get("fecha_fin").getAsString();
//				String nombre = objeto.get("nombre").getAsString();
//
//				Eventos evento = new Eventos(trabajador_id, fecha_inicio, fecha_fin, nombre);
//				listaEventos.add(evento);
//			}
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException(e);
//		}
//		for (int i = 0; i < listaEventos.size(); i++) {
//			System.out.println(listaEventos.get(i).getTurnoActualizado());
//		}

		File archivo = new File("C:\\Users\\varmido\\Desktop\\auxiliar\\springboot\\Informacion conocimientos operarios.xlsx");

		List<CloudVendor> operario = Poiji.fromExcel(archivo, CloudVendor.class);

		Random random = new Random();
		List<String> listaTurnos = Arrays.asList("Mañana","Tarde","Noche");
		for(CloudVendor obj : operario){
			obj.setEvento(false);
			obj.setUpdateTurno(false);
			int randomNumber = random.nextInt(3);
			obj.setTurno(listaTurnos.get(randomNumber));
			obj.setCategoria("Operario");
			if (obj.getConocimientos().equals("No hay segundo")){
				obj.setConocimientos("No tiene");
			}
			String ffregex = obj.getFechaBaja().replaceAll("/","-");
			String firegex = obj.getFechaInicio().replaceAll("/","-");

			obj.setFechaBaja(ffregex);
			obj.setFechaInicio(firegex);
			cloudVendorService.createCloudVendor(obj);
			System.out.println("Operario {%s} guardado satisfactoriamente".formatted(obj.getNombre()));
		}
		System.out.println("");
		System.out.println("------------------------------------------------------");
		System.out.println("%s operarios han sido guardados correctamente".formatted(operario.size()));
		System.out.println("------------------------------------------------------");




	}

}
