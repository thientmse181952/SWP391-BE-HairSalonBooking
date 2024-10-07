package com.example.demo.repository;

import com.example.demo.entity.CustomCollection;
import com.example.demo.entity.ServiceofHair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<CustomCollection, Long> {


    CustomCollection findCustomCollectionById(long id);

    List<CustomCollection> findServiceByIsDeletedFalse();
}
