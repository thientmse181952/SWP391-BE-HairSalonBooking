package com.example.demo.service;

import com.example.demo.entity.CustomCollection;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CollectionService {
    @Autowired
    CollectionRepository collectionRepository;


    public List<CustomCollection> getCollectionsByType(String type) {
        List<CustomCollection> collections = collectionRepository.findByType("someType");
        return collectionRepository.findByType(type);
    }

    public CustomCollection createNewCollection(CustomCollection customCollection) {
        try{
            CustomCollection newCustomCollection = collectionRepository.save(customCollection);
            return newCustomCollection;
        }catch(Exception e){
            throw new DuplicateEntity("Duplicate collection");
        }

    }
    public CustomCollection updateCollections(CustomCollection customCollection, long customeCollectionId) {

        CustomCollection oldCustomeCollection = collectionRepository.findCustomCollectionById(customeCollectionId);
        if (oldCustomeCollection == null) {
            throw new NotFoundException("Collection not found");
        }
        oldCustomeCollection.setCategory(customCollection.getCategory());
        oldCustomeCollection.setCollectionImage(customCollection.getCollectionImage());
        return collectionRepository.save(oldCustomeCollection);
    }
    public CustomCollection deleteCollections(long customeCollectionId){
        CustomCollection oldCustomCollection = collectionRepository.findCustomCollectionById(customeCollectionId);
        if (oldCustomCollection == null) {
            throw new NotFoundException("Collections not found");
        }
        oldCustomCollection.setDeleted(true);
        return collectionRepository.save(oldCustomCollection);
    }



}
