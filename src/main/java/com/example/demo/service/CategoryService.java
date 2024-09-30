package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        try{
            Category newCategory = categoryRepository.save(category);
            return newCategory;
        }catch(Exception e){
            throw new DuplicateEntity("Duplicate");
        }

    }
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findCategoriesByIsDeletedFalse();
        return categories;
    }

    public Category updatCategory(Category category, long categoryId) {

        Category oldCategory = categoryRepository.findCategoriesById(categoryId);
        if (oldCategory == null) {
            throw new NotFoundException("Category not found");
        }
        oldCategory.setNameCategory(category.getNameCategory());
        return categoryRepository.save(oldCategory);
    }
    public Category deleteCategory(long categoryId) {
        Category oldCategory = categoryRepository.findCategoriesById(categoryId);
        if (oldCategory == null) {
            throw new NotFoundException("Category not found");
        }
        oldCategory.setDeleted(true);
        return categoryRepository.save(oldCategory);
    }
}
