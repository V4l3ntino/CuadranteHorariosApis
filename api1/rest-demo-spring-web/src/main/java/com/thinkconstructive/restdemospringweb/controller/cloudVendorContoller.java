package com.thinkconstructive.restdemospringweb.controller;

import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import com.thinkconstructive.restdemospringweb.service.impl.CloudVendorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cloudvendor")
public class cloudVendorContoller {

    CloudVendorServiceImpl cloudVendorService;

    public cloudVendorContoller(CloudVendorServiceImpl cloudVendorService) {
        this.cloudVendorService = cloudVendorService;
    }
    //UN SOLO CLIENTE
    @GetMapping("/{vendorId}")
    public CloudVendor getCloudVendorDetails(@PathVariable Integer vendorId){
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
    public List<CloudVendor> getAllCloudVendorDetails(){
//        CloudVendor vendedor = null;
//        for (CloudVendor a : listaCloud){
//            if (a.getVendorId().equals(vendorId)){
//                vendedor = a;
//            }
//        }
        return cloudVendorService.getAllCloudVendors();
    }
    //CREAR UN CLIENTE
    @PostMapping
    public String createCloudVendorDetails(@RequestBody CloudVendor cloudVendor){
//        this.cloudVendor = cloudVendor;
//        listaCloud.add(cloudVendor);
        cloudVendorService.createCloudVendor(cloudVendor);
        return "EL CLIENTE HA SIDO CREADO EN LA BD";
    }
    @PutMapping
    public String updateCloudVendorDetails(@RequestBody CloudVendor cloudVendor){
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
        cloudVendorService.updateCloudVendor(cloudVendor);
        System.out.println("El %s cliente ha sido actualizado".formatted(cloudVendor.getNombre()));
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
        System.out.println("OPERARIO CON ID %s BORRADO EXITOSAMENTE".formatted(vendorId));
        return "EL CLIENTE SE HA ELIMINADO DE LA BD";

    }

}
