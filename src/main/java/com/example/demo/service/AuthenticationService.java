package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.AccountRepository;
import jakarta.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    // Temporary store for OTPs
    private Map<String, String> otpStore = new HashMap<>();
    private Map<String, Long> otpExpirationStore = new HashMap<>();

    // Define the expiration time for OTPs (e.g., 5 minutes)
    private static final long OTP_EXPIRATION_TIME = 300000; // in milliseconds

    private boolean isOtpExpired(Long expirationTime) {
        return expirationTime == null || System.currentTimeMillis() > expirationTime;
    }



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

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));

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

    public void changePassword(String phoneNumber, String currentPassword, String newPassword, String confirmPassword) {
        // Tìm người dùng theo số điện thoại
        Account user = accountRepository.findAccountByPhone(phoneNumber); // Updated to use Account class
        if (user == null) {
            throw new IllegalArgumentException("User not found."); // Handle user not found case
        }

        // Xác minh mật khẩu hiện tại
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        // Kiểm tra xem mật khẩu mới có khớp với mật khẩu xác nhận không
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirm password do not match.");
        }

        // Cập nhật mật khẩu
        user.setPassword(passwordEncoder.encode(newPassword)); // Lưu trữ mật khẩu mới đã đổi
        accountRepository.save(user); // Đã cập nhật để sử dụng AccountRepository
    }
    // Phương pháp tạo OTP 6 chữ số ngẫu nhiên
    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    // Phương thức gửi OTP tới số điện thoại của người dùng
    private void sendOtpToPhoneNumber(String phoneNumber, String otp) {
        // Mô phỏng gửi OTP (thay thế bằng logic gửi SMS thực tế)
        System.out.println("Sending OTP " + otp + " to phone number: " + phoneNumber);
    }

    // Phương pháp gửi OTP để reset mật khẩu
    public String sendResetPasswordOtp(String phoneNumber) {
        String otp = generateOtp(); // Giả sử có phương pháp tạo OTP
        otpStore.put(phoneNumber, otp);
        otpExpirationStore.put(phoneNumber, System.currentTimeMillis() + OTP_EXPIRATION_TIME);
        sendOtpToPhoneNumber(phoneNumber, otp);
        return otp; // Trả về OTP đã tạo
    }

    // Phương pháp đặt lại mật khẩu (được xác định trước đó)
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String phoneNumber = resetPasswordRequest.getPhoneNumber();
        String otp = resetPasswordRequest.getOtp();
        String currentPassword = resetPasswordRequest.getCurrentPassword();
        String newPassword = resetPasswordRequest.getNewPassword();
        String confirmPassword = resetPasswordRequest.getConfirmPassword();

        // Xác thực OTP
        String storedOtp = otpStore.get(phoneNumber);
        Long expirationTime = otpExpirationStore.get(phoneNumber);

        // Kiểm tra OTP có hợp lệ và chưa hết hạn không
        if (storedOtp == null || !storedOtp.equals(otp) || isOtpExpired(expirationTime)) {
            throw new IllegalArgumentException("Invalid or expired OTP.");
        }

        // Tìm người dùng theo số điện thoại
        Account user = accountRepository.findAccountByPhone(phoneNumber);
        if (user == null) {
            throw new IllegalArgumentException("User not found."); // Handle user not found case
        }


        // Kiểm tra xem mật khẩu mới có khớp với mật khẩu xác nhận không
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirm password do not match.");
        }

        // Cập nhật mật khẩu
        user.setPassword(passwordEncoder.encode(newPassword)); // Lưu trữ mật khẩu mới đã đô
        accountRepository.save(user); // Lưu người dùng đã cập nhật

        // Tùy chọn, xóa OTP sau khi xác thực thành công
        otpStore.remove(phoneNumber);
        otpExpirationStore.remove(phoneNumber);
    }

}
