package com.example.demo.repository;

import com.example.demo.entity.CategoryCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryCollectionRepository extends JpaRepository<CategoryCollection,Long> {
    CategoryCollection findServiceById(long id);

    // lấy danh sách những thằng student mà isDeleted = false
    List<CategoryCollection> findServiceByIsDeletedFalse();
}
