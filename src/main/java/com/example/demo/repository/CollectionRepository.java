package com.example.demo.repository;

import com.example.demo.entity.CustomCollection;
import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CollectionRepository extends JpaRepository<CustomCollection, Long> {

    CustomCollection findCustomCollectionById(long id);

    // lấy danh sách những thằng student mà isDeleted = false
    List<CustomCollection> findCollectionByIsDeletedFalse();


}
