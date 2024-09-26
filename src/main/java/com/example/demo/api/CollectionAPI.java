package com.example.demo.api;

import com.example.demo.entity.CustomCollection;
import com.example.demo.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/collection")
public class CollectionAPI {
    @Autowired
    CollectionService collectionService;
    @GetMapping
    public ResponseEntity getCollection() {
        List<CustomCollection> collectionList = collectionService.getAllCollections();
        return ResponseEntity.ok(collectionList);
    }
}
