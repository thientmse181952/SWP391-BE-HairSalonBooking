package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PasswordRequest {
    @NotBlank(message = "Phone number is required.")
    String phoneNumber;

    @NotBlank(message = "Current password is required.")
    String currentPassword;

    @NotBlank(message = "New password is required.")
    String newPassword;

    @NotBlank(message = "Confirm password is required.")
    String confirmPassword;


}
