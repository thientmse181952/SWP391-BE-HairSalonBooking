package com.example.demo.service;

import com.example.demo.entity.CustomCollection;
import com.example.demo.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CollectionService {
    @Autowired
    CollectionRepository collectionRepository;
    public List<CustomCollection> getAllCollections() {
        List<CustomCollection> collections = collectionRepository.findAll();
        return collections;
    }
}
