package com.example.demo.api;


import com.example.demo.entity.Booking;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Stylist;
import com.example.demo.model.StylistRequest;
import com.example.demo.service.CustomerService;
import com.example.demo.service.StylistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stylist")
@SecurityRequirement(name = "api")
public class StylistAPI {
    @Autowired
    StylistService stylistService;

@PostMapping
//@PreAuthorize("hasAuthority('MANAGER')")
//CHỈ NHỮNG THẰNG CÓ QUYỀN MANAGER MỚI ĐƯỢC TẠO
public ResponseEntity<Stylist> create(@Valid @RequestBody StylistRequest stylist) {
   Stylist newStylist =stylistService.createNewStylist(stylist);
   return ResponseEntity.ok(newStylist);
}
@GetMapping("getAllStylist")
    public ResponseEntity get() {
    List<Stylist> stylistList = stylistService.getAllStylists();
    return ResponseEntity.ok(stylistList);
}

@GetMapping("/{styListId}")
    public ResponseEntity<Stylist> getStylistById(@PathVariable Long styListId) {
        Stylist stylist = stylistService.getStylistById(styListId);
        return ResponseEntity.ok(stylist);
    }
@PutMapping("{id}")
    public ResponseEntity<Stylist> updateStylist (@Valid @RequestBody StylistRequest stylist, @PathVariable long id) {
    Stylist newStylist = stylistService.updateStylist(stylist, id);
    return ResponseEntity.ok(newStylist);
}

@PutMapping("/{stylistId}/rating")
    public ResponseEntity<Stylist> updateRating(@PathVariable Long stylistId, @RequestBody String newRating) {
        Stylist updateStylist = stylistService.updateRating(stylistId, newRating);
        return ResponseEntity.ok(updateStylist);
    }
@DeleteMapping("{id}")
    public ResponseEntity<Stylist> deleteStylist(@PathVariable long id) {
    Stylist stylist = stylistService.deleteStylist(id);
    return ResponseEntity.ok(stylist);
}

}
