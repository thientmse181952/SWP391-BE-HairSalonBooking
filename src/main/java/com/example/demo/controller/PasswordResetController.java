package com.example.demo.controller;

import com.example.demo.service.OtpService;
import com.example.demo.service.SmsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "api")
@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {
    @Autowired
    private OtpService otpService;

    @Autowired
    private SmsService smsService;

    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOtp(@RequestParam String phoneNumber) {
        // Validate Vietnamese phone number
        if (!isValidVietnamesePhoneNumber(phoneNumber)) {
            return ResponseEntity.badRequest().body("Invalid Vietnamese phone number");
        }

        String otp = otpService.generateOtp();
        // TODO: Store OTP and associate it with the user's account in your database

        // Send OTP via SMS
        try {
            smsService.sendSms(phoneNumber, "Your OTP for password reset is: " + otp);
            return ResponseEntity.ok("OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String phoneNumber, @RequestParam String otp, @RequestParam String newPassword) {
        // TODO: Verify OTP and update password in your database
        // This is a placeholder implementation
        if (verifyOtp(phoneNumber, otp)) {
            // Update password logic here
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

    private boolean isValidVietnamesePhoneNumber(String phoneNumber) {
        // Basic validation for Vietnamese phone numbers
        // You may want to use a more comprehensive regex or validation library
        String regex = "^(\\+84|84|0)[35789][0-9]{8}$";
        return phoneNumber.matches(regex);
    }

    private boolean verifyOtp(String phoneNumber, String otp) {
        // TODO: Implement OTP verification logic
        // This is a placeholder implementation
        return true;
    }
}
