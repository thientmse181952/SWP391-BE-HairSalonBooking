package com.example.demo.api;

import com.example.demo.entity.CategoryCollection;
import com.example.demo.entity.CustomCollection;

import com.example.demo.entity.Customer;
import com.example.demo.service.CollectionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@SecurityRequirement(name = "api")
@RestController
@RequestMapping("/api/collection")
public class CollectionAPI {
    @Autowired
    CollectionService collectionService;

    @PostMapping
    public ResponseEntity<CustomCollection> create(@Valid @RequestBody CustomCollection customCollection) {
        CustomCollection newCustomCollection =collectionService.createNewCollection(customCollection);
        return ResponseEntity.ok(newCustomCollection);
    }

    @GetMapping
    public ResponseEntity getCollections() {
        List<CustomCollection> collectiontList = collectionService.getAll();
        return ResponseEntity.ok(collectiontList);
    }


    @PutMapping("{id}")
    public ResponseEntity<CustomCollection> updateCollections (@Valid @RequestBody CustomCollection customCollection, @PathVariable long id) {
        CustomCollection newCustomCollection =collectionService.updateCollections(customCollection, id);
        return ResponseEntity.ok(newCustomCollection);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<CustomCollection> deleteCollections(@PathVariable long id) {
        CustomCollection customCollection = collectionService.deleteCollections(id);
        return ResponseEntity.ok(customCollection);
    }
}
