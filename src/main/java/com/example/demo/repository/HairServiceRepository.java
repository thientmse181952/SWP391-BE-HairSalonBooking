package com.example.demo.repository;

import com.example.demo.entity.CustomCollection;
import com.example.demo.entity.ServiceofHair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HairServiceRepository extends JpaRepository<ServiceofHair, Long> {

    // Tìm 1 thằng student bằng id của nó
    // find + Student + By + Id(long id)
    ServiceofHair findServiceById(long id);

    // lấy danh sách những thằng student mà isDeleted = false
    List<ServiceofHair> findServiceByIsDeletedFalse();

    List<ServiceofHair> findByType(String type);
}
