package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.exception.DupicateEntity;
import com.example.demo.repository.AccountRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    AccountRepository accountRepository;

    public Account register(Account account) {
        try {
            Account newAccount = accountRepository.save(account);
            return newAccount;
        } catch (Exception e) {
            if(e.getMessage().contains(account.getCode())) {
                throw new DupicateEntity("Duplicate Code!");
            } else if (e.getMessage().contains(account.getEmail())) {

                throw new DupicateEntity("Duplicate Email!");
            }else{
                throw new DupicateEntity("Duplicate Phone!");
            }

        }

    }
    public List<Account> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }
    public Account login() {
        return null;
    }


}
