package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Booking;
import com.example.demo.entity.ServiceofStylist;
import com.example.demo.entity.Stylist;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.StylistRequest;
import com.example.demo.repository.HairServiceRepository;
import com.example.demo.repository.StylistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StylistService {

    @Autowired
    StylistRepository stylistRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    HairServiceRepository hairServiceRepository;

    public Stylist createNewStylist(StylistRequest stylistRequest) {
        Stylist stylist = modelMapper.map(stylistRequest, Stylist.class);

        Account account = authenticationService.getCurrentAccount();
        stylist.setAccount(account);

        Set<ServiceofStylist> serviceofStylists = new HashSet<>();
        for(Long idService : stylistRequest.getService_id()){
            ServiceofStylist serviceofStylist = hairServiceRepository.findById(idService).orElseThrow(() -> new NotFoundException("Service not found"));
            serviceofStylists.add(serviceofStylist);
        }

        stylist.setServiceofStylists(serviceofStylists);


        try{
            Stylist newStylist = stylistRepository.save(stylist);
            return newStylist;
        }catch(Exception e){
                  throw new DuplicateEntity("Duplicate stylist found");
        }

    }
    public List<Stylist> getAllStylists() {
        return stylistRepository.findAll();
    }

    public Stylist getStylistById(Long stylistId) {
        return stylistRepository.findById(stylistId).orElseThrow(() -> new NotFoundException("Stylist not found"));

    }

public Stylist updateStylist(StylistRequest stylistRequest, long stylistId) {
    Stylist stylist = modelMapper.map(stylistRequest, Stylist.class);
    Set<ServiceofStylist> serviceofStylists = new HashSet<>();
    for(Long idService : stylistRequest.getService_id()){
        ServiceofStylist serviceofStylist = hairServiceRepository.findById(idService).orElseThrow(() -> new NotFoundException("Service not found"));
        serviceofStylists.add(serviceofStylist);
    }

    Stylist oldStylist = stylistRepository.findStylistsById(stylistId);
    if (oldStylist == null) {
        throw new NotFoundException("Stylist not found");
    }
    oldStylist.setImage(stylist.getImage());
    oldStylist.setServiceofStylists(serviceofStylists);

    return stylistRepository.save(oldStylist);
}
  public Stylist deleteStylist(long stylistId) {
      Stylist oldStylist = stylistRepository.findStylistsById(stylistId);
      if (oldStylist == null) {
          throw new NotFoundException("Stylist not found");
      }
      oldStylist.setDeleted(true);
      return stylistRepository.save(oldStylist);
  }

}
