package com.example.demo.service;

import com.example.demo.entity.ServiceofStylist;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.ServiceRequest;
import com.example.demo.repository.HairServiceRepository;
import com.example.demo.repository.StylistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HairService {

    @Autowired
    HairServiceRepository hairServiceRepository;

    @Autowired
    ModelMapper modelMapper;



    public List<ServiceofStylist> getAll() {
        List<ServiceofStylist> serviceofHairs = hairServiceRepository.findServiceByIsDeletedFalse();
        return serviceofHairs;
    }

    public ServiceofStylist createNewService(ServiceRequest serviceRequest) {
        ServiceofStylist newservice = modelMapper.map(serviceRequest, ServiceofStylist.class);


        try{
            ServiceofStylist newserviceHair = hairServiceRepository.save(newservice);
            return newserviceHair;
        }catch(Exception e){
            throw new DuplicateEntity("Duplicate Service");
        }

    }
    public ServiceofStylist updateService(ServiceofStylist serviceofHair, long serivceofHairId) {

        ServiceofStylist oldServiceofHair = hairServiceRepository.findServiceById(serivceofHairId);
        if (oldServiceofHair == null) {
            throw new NotFoundException("Service not found");
        }
        oldServiceofHair.setName(serviceofHair.getName());
        oldServiceofHair.setServiceImage(serviceofHair.getServiceImage());
        oldServiceofHair.setCategory(serviceofHair.getCategory());
        oldServiceofHair.setDescription(serviceofHair.getDescription());
        oldServiceofHair.setDuration(serviceofHair.getDuration());
        oldServiceofHair.setPrice(serviceofHair.getPrice());

        return hairServiceRepository.save(oldServiceofHair);
    }
    public ServiceofStylist deleteService(long serivceofHairId) {
        ServiceofStylist oldServiceofHair = hairServiceRepository.findServiceById(serivceofHairId);
        if (oldServiceofHair == null) {
            throw new NotFoundException("Service not found");
        }
        oldServiceofHair.setDeleted(true);
        return hairServiceRepository.save(oldServiceofHair);
    }
}
