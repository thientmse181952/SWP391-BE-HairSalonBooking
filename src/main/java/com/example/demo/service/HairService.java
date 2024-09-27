package com.example.demo.service;

import com.example.demo.entity.CustomCollection;
import com.example.demo.entity.ServiceofHair;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.HairServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class HairService {

    @Autowired
    HairServiceRepository hairServiceRepository;

    public List<ServiceofHair> getAllService() {
        List<ServiceofHair> serviceHair = hairServiceRepository.findServiceByIsDeletedFalse();
        return serviceHair;
    }

    public ServiceofHair createNewService(ServiceofHair serviceHair) {
        try{
            ServiceofHair newserviceHair = hairServiceRepository.save(serviceHair);
            return newserviceHair;
        }catch(Exception e){
            throw new DuplicateEntity("Duplicate Service");
        }

    }
    public ServiceofHair updateService(ServiceofHair serviceofHair, long serivceofHairId) {

        ServiceofHair oldServiceofHair = hairServiceRepository.findServiceById(serivceofHairId);
        if (oldServiceofHair == null) {
            throw new NotFoundException("Service not found");
        }
        oldServiceofHair.setName(serviceofHair.getName());
        oldServiceofHair.setServiceImage(serviceofHair.getServiceImage());
        oldServiceofHair.setCategory(serviceofHair.getCategory());
        oldServiceofHair.setDescription(serviceofHair.getDescription());
        oldServiceofHair.setDuration(serviceofHair.getDuration());
        oldServiceofHair.setPrice(serviceofHair.getPrice());
        oldServiceofHair.setStylist(serviceofHair.getStylist());

        return hairServiceRepository.save(oldServiceofHair);
    }
    public ServiceofHair deleteService(long serivceofHairId) {
        ServiceofHair oldServiceofHair = hairServiceRepository.findServiceById(serivceofHairId);
        if (oldServiceofHair == null) {
            throw new NotFoundException("Service not found");
        }
        oldServiceofHair.setDeleted(true);
        return hairServiceRepository.save(oldServiceofHair);
    }
}
