package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    EmailService emailService;


    public AccountResponse register(RegisterRequest registerRequest) {
        // map RegisterRequest => Account
        Account account = modelMapper.map(registerRequest, Account.class);

        // Kiểm tra gender ngay lập tức
        if (!account.getGender().equals("Male") && !account.getGender().equals("Female")) {
            throw new IllegalArgumentException("Not Valid Gender!");
        }

        // Kiểm tra xem số điện thoại đã tồn tại chưa
        if (accountRepository.findAccountByPhone(account.getPhone()) != null) {
            throw new DuplicateEntity("Duplicate phone!");
        }

        try {
            // Mã hóa mật khẩu
            String originPassword = account.getPassword();
            account.setPassword(passwordEncoder.encode(originPassword));

            // Lưu tài khoản mới vào database
            Account newAccount = accountRepository.save(account);
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setAccount(newAccount);
            emailDetail.setSubject("Welcome to Hair Salon");
            // chen link wweb vao day
            emailDetail.setLink("https://www.google.com/");
            emailService.sentEmail(emailDetail);


            // Trả về thông tin tài khoản đã tạo
            return modelMapper.map(newAccount, AccountResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }



    public List<Account> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    public AccountResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));
            Account account = (Account) authentication.getPrincipal();
            AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
            accountResponse.setToken(tokenService.generateToken(account));
            return accountResponse;
        } catch (Exception e) {
            throw new NotFoundException("Username or password invalid!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return accountRepository.findAccountByPhone(phone);
    }

    //ai đang gọi cái request này
    public Account getCurrentAccount(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findAccountById(account.getId());
    }
    public Account updateAuthentication(Long authId, ChangInforRequest request) {
        Account auth = accountRepository.findById(authId)
                .orElseThrow(() -> new NotFoundException("Authentication not found for this id: " + authId));

        auth.setFullName(request.getFullName());
        auth.setGender(request.getGender());
        auth.setEmail(request.getEmail());

        return accountRepository.save(auth);
    }
    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        // Get the current authenticated account
        Account account = getCurrentAccount();

        // Verify that the current password matches the one in the database
        if (!passwordEncoder.matches(currentPassword, account.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        // Verify that the new password matches the confirm password
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirm password do not match.");
        }

        // Set the new password and save the account
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
    }


}
