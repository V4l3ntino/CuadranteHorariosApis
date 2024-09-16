package com.thinkconstructive.restdemospringweb.service.impl;

import com.thinkconstructive.restdemospringweb.model.Eventos;
import com.thinkconstructive.restdemospringweb.repository.EventosRepository;
import com.thinkconstructive.restdemospringweb.service.CloudVendorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventosServiceImpl implements CloudVendorServiceInterface<Eventos> {

    @Autowired
    EventosRepository eventosRepository;

    public EventosServiceImpl(EventosRepository eventosRepository) {
        this.eventosRepository = eventosRepository;
    }

    @Override
    public String createCloudVendor(Eventos cloudVendor) {
        eventosRepository.save(cloudVendor);
        return "";
    }

    @Override
    public String updateCloudVendor(Eventos cloudVendor) {
        eventosRepository.save(cloudVendor);
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
}
