package com.example.demo.api;


import com.example.demo.entity.CategoryCollection;
import com.example.demo.service.CategoryCollectionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-collection")
@SecurityRequirement(name = "api")
public class CategoryCollectionAPI {
    @Autowired
    CategoryCollectionService categoryCollectionService;

@PostMapping
//@PreAuthorize("hasAuthority('MANAGER')")
//CHỈ NHỮNG THẰNG CÓ QUYỀN MANAGER MỚI ĐƯỢC TẠO
public ResponseEntity<CategoryCollection> create(@Valid @RequestBody CategoryCollection categoryCollection) {
   CategoryCollection newCategory =categoryCollectionService.createCategory(categoryCollection);
   return ResponseEntity.ok(newCategory);
}
@GetMapping("/{categoryServiceId}")
    public ResponseEntity<CategoryCollection> getCategory(@PathVariable Long categoryServiceId) {
        CategoryCollection categoryCollection = categoryCollectionService.getCategoryById(categoryServiceId);
        return ResponseEntity.ok(categoryCollection);
    }
@GetMapping("/getCollection")
    public ResponseEntity getCollections() {
        List<CategoryCollection> categoryCollections = categoryCollectionService.getAll();
        return ResponseEntity.ok(categoryCollections);
    }
@PutMapping("{categoryId}")
    public ResponseEntity<CategoryCollection> updateCategory (@Valid @RequestBody CategoryCollection categoryCollection, @PathVariable long categoryId) {
    CategoryCollection newCategory = categoryCollectionService.updateCategory(categoryCollection, categoryId);
    return ResponseEntity.ok(newCategory);
}
@DeleteMapping("{categoryId}")
    public ResponseEntity<CategoryCollection> deleteCategory(@PathVariable long categoryId) {
    CategoryCollection categoryCollection = categoryCollectionService.deleteCategory(categoryId);
    return ResponseEntity.ok(categoryCollection);
}

}
