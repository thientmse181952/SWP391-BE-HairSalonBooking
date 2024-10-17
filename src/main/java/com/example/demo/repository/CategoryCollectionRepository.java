package com.example.demo.repository;

import com.example.demo.entity.CategoryCollection;
import com.example.demo.entity.Stylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryCollectionRepository extends JpaRepository<CategoryCollection, Long> {

    // Tìm 1 thằng student bằng id của nó
    // find + Student + By + Id(long id)
    CategoryCollection findCategoryCollectionById(long id);

    // lấy danh sách những thằng student mà isDeleted = false
    List<CategoryCollection> findCategoryCollectionByIsDeletedFalse();
}
