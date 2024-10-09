package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Tìm 1 thằng student bằng id của nó
    // find + Student + By + Id(long id)
    Customer findCustomerById(long id);

    // lấy danh sách những thằng student mà isDeleted = false
    List<Customer> findCustomersByIsDeletedFalse();

}
