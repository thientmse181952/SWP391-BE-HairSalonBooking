package com.example.demo.repository;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // lấy account bằng phone
    Account findAccountByPhone(String phone);

    //Account findByUsername(String username);

    Account findAccountById(Long id);

    @Query("select count(a) from Account a where a.role = :role")
    long countByRole(@Param("role") Role role);

}
