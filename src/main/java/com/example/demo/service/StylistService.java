package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Stylist;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.StylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StylistService {

    @Autowired
    StylistRepository stylistRepository;

    public Stylist createNewStylist(Stylist stylist) {
        try{
            Stylist newStylist = stylistRepository.save(stylist);
            return newStylist;
        }catch(Exception e){
                  throw new DuplicateEntity("Duplicate stylist found");
        }

    }
   public List<Stylist> getAllStylist() {
        List<Stylist> stylists = stylistRepository.findStylistsByIsDeletedFalse();
        return stylists;
    }

public Stylist updateStylist(Stylist stylist, long stylistId) {

    Stylist oldStylist = stylistRepository.findStylistsById(stylistId);
    if (oldStylist == null) {
        throw new NotFoundException("Stylist not found");
    }
    oldStylist.setName(stylist.getName());
    oldStylist.setGender(stylist.getGender());
    oldStylist.setImage(stylist.getImage());
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
