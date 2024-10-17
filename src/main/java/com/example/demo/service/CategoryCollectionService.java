package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.CategoryCollection;
import com.example.demo.entity.CustomCollection;
import com.example.demo.entity.ServiceofStylist;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.BookingRequest;
import com.example.demo.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryCollectionService {

    @Autowired
    CategoryCollectionRepository categoryCollectionRepository;

    @Autowired
    ModelMapper modelMapper;


    public CategoryCollection getCategoryById(Long categoryId) {
        return categoryCollectionRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + categoryId));
    }

    public CategoryCollection createCategory(CategoryCollection categoryCollection) {

        try{
            CategoryCollection newCategory = categoryCollectionRepository.save(categoryCollection);
            return newCategory;
        }catch(Exception e){
            throw new DuplicateEntity("Duplicate category found");
        }
    }
    public CategoryCollection updateCategory(CategoryCollection categoryCollection, long categoryId) {

        CategoryCollection oldCategory = categoryCollectionRepository.findCategoryCollectionById(categoryId);
        if (oldCategory == null) {
            throw new NotFoundException("Category not found");
        }
       oldCategory.setNameCategory(categoryCollection.getNameCategory());
        return categoryCollectionRepository.save(oldCategory);
    }

    public CategoryCollection deleteCategory(long categoryId) {
        CategoryCollection categoryCollection = categoryCollectionRepository.findCategoryCollectionById(categoryId);
        if (categoryCollection == null) {
            throw new NotFoundException("Collections not found");
        }
        categoryCollection.setDeleted(true);
        return categoryCollectionRepository.save(categoryCollection);
    }
}