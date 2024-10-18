package com.example.demo.model;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    String phoneNumber;
    String otp;
    String newPassword;
    String confirmPassword;
}
