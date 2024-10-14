package com.example.demo.controller;

import com.example.demo.service.OtpService;
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

    @PostMapping("/request-otp")
    public String requestOtp(@RequestParam String phoneNumber) {
        String otp = otpService.generateOtp();
        boolean isSent = otpService.sendOtp(phoneNumber, otp);

        if (isSent) {
            // Here you should store the OTP securely for later verification (e.g., in-memory store, cache, etc.)
            // For demonstration, we'll return the OTP in response (don't do this in production)
            return "OTP sent successfully: " + otp; // Remove this in production
        } else {
            return "Failed to send OTP.";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String phoneNumber, @RequestParam String otp, @RequestParam String newPassword) {
        // Here you should verify the OTP from your temporary store
        boolean isOtpValid = true; // Replace with actual verification logic

        if (isOtpValid) {
            // Update the user's password in the database
            return "Password reset successfully.";
        } else {
            return "Invalid OTP.";
        }
    }
}
