package com.example.demo.repository;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // lấy account bằng phone
    Account findAccountByPhone(String phone);

    Account findAccountById(Long id);


}
