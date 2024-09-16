package com.thinkconstructive.restdemospringweb.service.impl;

import com.thinkconstructive.restdemospringweb.model.EventosApi1;
import com.thinkconstructive.restdemospringweb.repository.EventosApi1Repository;
import com.thinkconstructive.restdemospringweb.service.CloudVendorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventosApi1Service implements CloudVendorServiceInterface {

    @Autowired
    EventosApi1Repository eventosApi1Repository;

    @Override
    public String createCloudVendor(Object cloudVendor) {
        return "";
    }

    @Override
    public String updateCloudVendor(Object cloudVendor) {
        return "";
    }

    @Override
    public String deleteCloudVendor(Integer cloudVendorId) {
        return "";
    }

    @Override
    public Object getCloudVendor(Integer cloudVendorId) {
        return null;
    }

    @Override
    public List getAllCloudVendors() {
        return List.of();
    }

    @Override
    public void deleteAll() {
        List<EventosApi1> listaEventos = eventosApi1Repository.findAll();
        for (EventosApi1 evento : listaEventos){
            if(evento.getCreador() == null){
                eventosApi1Repository.deleteById(evento.getId());
            }
        }
    }
}
