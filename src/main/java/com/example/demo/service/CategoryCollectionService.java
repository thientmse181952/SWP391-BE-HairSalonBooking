//package com.example.demo.service;
//
//import com.example.demo.entity.CategoryCollection;
//import com.example.demo.exception.DuplicateEntity;
//import com.example.demo.exception.NotFoundException;
//import com.example.demo.repository.CategoryCollectionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CategoryCollectionService {
//
//    @Autowired
//    CategoryCollectionRepository selectServiceRepository;
//
//    public List<CategoryCollection> getCategoryByType(String type) {
//        List<CategoryCollection> categoryCollections = selectServiceRepository.findByType("someType");
//        return selectServiceRepository.findByType(type);
//    }

//    public CategoryCollection createNewSelectService(CategoryCollection select) {
//        try{
//            CategoryCollection newSelect = selectServiceRepository.save(select);
//            return newSelect;
//        }catch(Exception e){
//                  throw new DuplicateEntity("Duplicate select found");
//        }
//
//    }
//   public List<CategoryCollection> getAllSelect() {
//        List<CategoryCollection> select = selectServiceRepository.findServiceByIsDeletedFalse();
//        return select;
//    }
//
//public CategoryCollection update(CategoryCollection select, long selectedId) {
//
//    CategoryCollection oldSelect = selectServiceRepository.findServiceById(selectedId);
//    if (oldSelect == null) {
//        throw new NotFoundException("Select not found");
//    }
//    oldSelect.setNameCategory(select.getNameCategory());
//    oldSelect.setDeleted(select.isDeleted());
//    return selectServiceRepository.save(oldSelect);
//}
//  public CategoryCollection delete(long selectedId) {
//      CategoryCollection oldSelect = selectServiceRepository.findServiceById(selectedId);
//      if (oldSelect == null) {
//          throw new NotFoundException("Select not found");
//      }
//      oldSelect.setDeleted(true);
//      return selectServiceRepository.save(oldSelect);
//  }


