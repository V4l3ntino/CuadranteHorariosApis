package com.thinkconstructive.restdemospringweb.service.impl;

import com.thinkconstructive.restdemospringweb.model.*;
import com.thinkconstructive.restdemospringweb.repository.*;
import com.thinkconstructive.restdemospringweb.service.CloudVendorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CloudVendorServiceImpl implements CloudVendorServiceInterface<CloudVendor> {
    @Autowired
    CloudVendorRepository cloudVendorRepository;
    @Autowired
    EventosRepository eventosRepository;
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @Autowired
    ParteRepository parteRepository;
    @Autowired
    PermutasRepository permutasRepository;
    @Autowired
    FaltasRepository faltasRepository;

    public CloudVendorServiceImpl(CloudVendorRepository cloudVendorRepository) {
        this.cloudVendorRepository = cloudVendorRepository;
    }

    @Override
    public String createCloudVendor(CloudVendor cloudVendor) {
        cloudVendorRepository.save(cloudVendor);
        return "Success";
    }

    @Override
    public String updateCloudVendor(CloudVendor cloudVendor) {
        cloudVendorRepository.save(cloudVendor);
        return "Success";
    }

    @Override
    public String deleteCloudVendor(Integer cloudVendorId) {
        List<Eventos> listaEventos = eventosRepository.findAll();
        List<Parte> listaPartes = parteRepository.findAll();
        List<Incidencia> listaIncidencia = incidenciaRepository.findAll();
        List<Permutas> listaPermutas = permutasRepository.findAll();
        List<Faltas> listaFaltas = faltasRepository.findAll();

        if (!listaPartes.isEmpty()){
            for(Parte parte : listaPartes){
                if(parte.getOperario_id().equals(cloudVendorId)){
                    parteRepository.deleteById(parte.getId());
                }
            }
        }

        if (!listaFaltas.isEmpty()){
            for(Faltas falta : listaFaltas){
                if(falta.getUsuario_id().equals(cloudVendorId)){
                    faltasRepository.deleteById(falta.getId());
                }
            }
        }

        if (!listaIncidencia.isEmpty()){
            for(Incidencia incidencia : listaIncidencia){
                if(incidencia.getOperario_id().equals(cloudVendorId)){
                    incidenciaRepository.deleteById(incidencia.getId());
                }
            }
        }

        if (!listaPermutas.isEmpty()){
            for(Permutas permuta : listaPermutas){
                if(permuta.getUsuario_id().equals(cloudVendorId)){
                    permutasRepository.deleteById(permuta.getId());
                }
            }
        }

        if (!listaEventos.isEmpty()){
            for(Eventos evento : listaEventos){
                if(evento.getUsuario_id().equals(cloudVendorId)){
                    eventosRepository.deleteById(evento.getId());
                }
            }
            cloudVendorRepository.deleteById(cloudVendorId);
        }else {
            cloudVendorRepository.deleteById(cloudVendorId);
        }

        return "Success";
    }

    @Override
    public CloudVendor getCloudVendor(Integer cloudVendorId) {
        return cloudVendorRepository.findById(cloudVendorId).get();
    }

    @Override
    public List<CloudVendor> getAllCloudVendors() {
        return cloudVendorRepository.findAll();
    }

    @Override
    public void deleteAll() {

    }
}
