package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoriesById (long id);

    // lấy danh sách những thằng student mà isDeleted = false
    List<Category> findCategoriesByIsDeletedFalse();



}
