package com.example.demo.repository;

import com.example.demo.entity.CustomCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CollectionRepository extends JpaRepository<CustomCollection, Long> {

    CustomCollection findById(long id);


}
