package com.example.demo.api;

import com.example.demo.entity.CustomCollection;
import com.example.demo.entity.ServiceofHair;
import com.example.demo.service.HairService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/service")
@RestController
public class HairServiceAPI {

    @Autowired
    HairService hairService;

    @PostMapping
    public ResponseEntity<ServiceofHair> createService(@Valid @RequestBody ServiceofHair serviceofHair) {
        ServiceofHair newServiceofHair =hairService.createNewService(serviceofHair);
        return ResponseEntity.ok(newServiceofHair);
    }

    @GetMapping
    public ResponseEntity get() {
        List<ServiceofHair> hairServiceList = hairService.getAllService();
        return ResponseEntity.ok(hairServiceList);
    }

    @PutMapping("{id}")
    public ResponseEntity<ServiceofHair> updateService(@Valid @RequestBody ServiceofHair serviceofHair, @PathVariable long id) {
        ServiceofHair newServiceofHair =hairService.updateService(serviceofHair, id);
        return ResponseEntity.ok(newServiceofHair);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ServiceofHair> deleteService(@PathVariable long id) {
        ServiceofHair serviceofHair = hairService.deleteService(id);
        return ResponseEntity.ok(serviceofHair);
    }


}
