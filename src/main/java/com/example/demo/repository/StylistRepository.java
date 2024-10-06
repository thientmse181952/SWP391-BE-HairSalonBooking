package com.example.demo.repository;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Stylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StylistRepository extends JpaRepository<Stylist, Long> {

    // Tìm 1 thằng student bằng id của nó
    // find + Student + By + Id(long id)
    Stylist findStylistsById(long id);

    // lấy danh sách những thằng student mà isDeleted = false
    List<Stylist> findStylistsByIsDeletedFalse();
}
