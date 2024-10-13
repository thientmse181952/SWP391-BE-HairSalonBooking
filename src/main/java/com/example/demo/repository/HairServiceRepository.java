package com.example.demo.repository;

import com.example.demo.entity.ServiceofStylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HairServiceRepository extends JpaRepository<ServiceofStylist, Long> {

    // Tìm 1 thằng student bằng id của nó
    // find + Student + By + Id(long id)
    ServiceofStylist findServiceById(long id);

    // lấy danh sách những thằng student mà isDeleted = false
    List<ServiceofStylist> findServiceByIsDeletedFalse();


}
