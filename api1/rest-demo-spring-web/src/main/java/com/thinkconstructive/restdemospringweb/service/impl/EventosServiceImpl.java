package com.thinkconstructive.restdemospringweb.service.impl;

import com.thinkconstructive.restdemospringweb.model.Eventos;
import com.thinkconstructive.restdemospringweb.model.CloudVendor;
import com.thinkconstructive.restdemospringweb.repository.CloudVendorRepository;
import com.thinkconstructive.restdemospringweb.repository.EventosRepository;
import com.thinkconstructive.restdemospringweb.service.CloudVendorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EventosServiceImpl implements CloudVendorServiceInterface<Eventos> {

    @Autowired
    EventosRepository eventosRepository;
    @Autowired
    CloudVendorServiceImpl cloudVendorService;

    public EventosServiceImpl(EventosRepository eventosRepository) {
        this.eventosRepository = eventosRepository;
    }

    @Override
    public String createCloudVendor(Eventos cloudVendor) {
        if (eventosRepository.existsById(cloudVendor.getId())) {
            System.out.println("El evento con id %s ACTUALIZADO".formatted(cloudVendor.getId()));
        } else {
            System.out.println("El evento con id %s creado".formatted(cloudVendor.getId()));
        }
        eventosRepository.save(cloudVendor);
        return "";
    }

    @Override
    public String updateCloudVendor(Eventos cloudVendor) {
        eventosRepository.save(cloudVendor);
        CloudVendor user = cloudVendorService.getCloudVendor(cloudVendor.getUsuario_id());
        user.setEvento(true);
        cloudVendorService.updateCloudVendor(user);
        System.out.println("Evento guardado para el usuario " + cloudVendor.getUsuario_id());

        return "";
    }

    @Override
    public String deleteCloudVendor(Integer cloudVendorId) {
        eventosRepository.deleteById(cloudVendorId);
        return "";
    }

    @Override
    public Eventos getCloudVendor(Integer cloudVendorId) {
        return eventosRepository.findById(cloudVendorId).get();
    }

    @Override
    public List<Eventos> getAllCloudVendors() {
        return eventosRepository.findAll();
    }

    @Override
    public void deleteAll() {
        List<Eventos> eventosCreados = new ArrayList<>();
        List<Eventos> listaEventos = eventosRepository.findAll();
        if (listaEventos.size() > 0) {
            for (Eventos event : listaEventos) {
                if (event.getCreador() != null) {
                    eventosCreados.add(event);
                }
            }
            eventosRepository.deleteAll();
            for (Eventos event : eventosCreados) {
                eventosRepository.save(event);
            }
            System.out.println("BASE DE DATOS LISTA PARA ACTUALIZAR:");
        }
    }
}
