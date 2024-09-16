package com.thinkconstructive.restdemospringweb.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import com.thinkconstructive.restdemospringweb.model.Eventos;
import com.thinkconstructive.restdemospringweb.service.impl.CloudVendorServiceImpl;
import com.thinkconstructive.restdemospringweb.service.impl.EventosServiceImpl;
@RestController
@RequestMapping("/eventos")
public class eventosController {
     EventosServiceImpl cloudVendorService;

    public eventosController(EventosServiceImpl cloudVendorService) {
        this.cloudVendorService = cloudVendorService;
    }
    //UN SOLO CLIENTE
    @GetMapping("/{vendorId}")
    public Eventos getCloudVendorDetails(@PathVariable Integer vendorId){
//        CloudVendor vendedor = null;
//        for (CloudVendor a : listaCloud){
//            if (a.getVendorId().equals(vendorId)){
//                vendedor = a;
//            }
//        }
        return cloudVendorService.getCloudVendor(vendorId);
    }
    // LISTA DE TODOS LOS CLIENTES
    @GetMapping()
    public List<Eventos> getAllCloudVendorDetails(){
//        CloudVendor vendedor = null;
//        for (CloudVendor a : listaCloud){
//            if (a.getVendorId().equals(vendorId)){
//                vendedor = a;
//            }
//        }
        return cloudVendorService.getAllCloudVendors();
    }
    @GetMapping("/deleteall")
    public void deleteAllEvents(){
        cloudVendorService.deleteAll();
    }
    //CREAR UN CLIENTE
    @PostMapping
    public String createCloudVendorDetails(@RequestBody Eventos cloudVendor){
//        this.cloudVendor = cloudVendor;
//        listaCloud.add(cloudVendor);
        cloudVendorService.createCloudVendor(cloudVendor);
        return "EL CLIENTE HA SIDO CREADO EN LA BD";
    }
    @PutMapping
    public String updateCloudVendorDetails(@RequestBody Eventos cloudVendor){
//        try {
//            for(int i = 0; i < listaCloud.size(); i++){
//                CloudVendor a = listaCloud.get(i);
//                if(a.getVendorId().equals(cloudVendor.getVendorId())){
//                    listaCloud.set(i, cloudVendor);
//                    return ResponseEntity.ok("EL USUARIO HA SIDO ACTUALIZADO CORRECTAMENTE, BOMBÓN");
//                }
//            }
//            return ResponseEntity.notFound().build();
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
        System.out.println(cloudVendor);
        cloudVendorService.updateCloudVendor(cloudVendor);
        return "EL CLIENTE HA SIDO ACTUALIZADO CORRECTAMENTE";
    }
    @DeleteMapping("/{vendorId}")
    public String deleteCloudVendorDetails(@PathVariable Integer vendorId){
//        try{
//            for (int i = 0; i < listaCloud.size(); i++){
//                CloudVendor a = listaCloud.get(i);
//                if(a.getVendorId().equals(vendorId)){
//                    listaCloud.remove(i);
//                    return ResponseEntity.ok("EL USUARIO HA SIDO BORRADO CORRECTAMENTE");
//                }
//            }
//            return ResponseEntity.notFound().build();
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
        cloudVendorService.deleteCloudVendor(vendorId);
        return "EL CLIENTE SE HA ELIMINADO DE LA BD";

    }

}
