package com.example.demo.api;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "api")
@RestController
@RequestMapping("/api/category")
public class CategoryAPI {

    @Autowired
    CategoryService categoryService;
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category newCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(newCategory);
    }

    @GetMapping("getCategory")
    public ResponseEntity getCategory() {
        List<Category> categoryList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryList);
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory (@Valid @RequestBody Category category, @PathVariable long id) {
        Category newCategory =categoryService.updatCategory(category, id);
        return ResponseEntity.ok(newCategory);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable long id) {
        Category category = categoryService.deleteCategory(id);
        return ResponseEntity.ok(category);
    }
}
