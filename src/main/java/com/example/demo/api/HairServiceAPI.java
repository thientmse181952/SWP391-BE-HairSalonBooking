package com.example.demo.api;

import com.example.demo.entity.Booking;
import com.example.demo.entity.ServiceofStylist;
import com.example.demo.model.HairServiceRequest;
import com.example.demo.model.ServiceRequest;
import com.example.demo.service.HairService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "api")
@RequestMapping("/api/service")
@RestController
public class HairServiceAPI {

    @Autowired
    HairService hairService;

    @PostMapping
    public ResponseEntity<ServiceofStylist> createService(@Valid @RequestBody HairServiceRequest serviceofHair) {
        ServiceofStylist newServiceofHair =hairService.createNewService(serviceofHair);
        return ResponseEntity.ok(newServiceofHair);
    }

    @GetMapping("/getService")
    public ResponseEntity get() {
        List<ServiceofStylist> serviceofHairs = hairService.getAll();
        return ResponseEntity.ok(serviceofHairs);
    }

    @GetMapping("/{getServiceId}")
    public ResponseEntity<ServiceofStylist> getBookingById(@PathVariable Long getServiceId) {
        ServiceofStylist serviceofStylist = hairService.getServiceById(getServiceId);
        return ResponseEntity.ok(serviceofStylist);
    }


    @PutMapping("{id}")
    public ResponseEntity<ServiceofStylist> updateService(@Valid @RequestBody ServiceofStylist serviceofHair, @PathVariable long id) {
        ServiceofStylist newServiceofHair =hairService.updateService(serviceofHair, id);
        return ResponseEntity.ok(newServiceofHair);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ServiceofStylist> deleteService(@PathVariable long id) {
        ServiceofStylist serviceofHair = hairService.deleteService(id);
        return ResponseEntity.ok(serviceofHair);
    }


}
