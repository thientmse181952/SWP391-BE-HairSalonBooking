package com.example.demo.api;

import com.example.demo.entity.Account;
import com.example.demo.model.*;
import com.example.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "api")
@RestController
@RequestMapping("api")

public class AuthenticationAPI {

    // DI: Dependency Injection
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        AccountResponse newAccount = authenticationService.register(registerRequest);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        AccountResponse newAccount = authenticationService.login(loginRequest);
        return ResponseEntity.ok(newAccount);
    }

    @GetMapping("account")
    public ResponseEntity getAllAccount() {
        List<Account> accounts = authenticationService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        Account updateAccount = authenticationService.getAccountById(accountId);
        return ResponseEntity.ok(updateAccount);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAuthentication(
            @PathVariable(value = "id") Long authId,  // Ensure this matches the path
            @RequestBody @Validated ChangInforRequest request) {
        Account updatedAuth = authenticationService.updateAuthentication(authId, request);
        return ResponseEntity.ok(updatedAuth);
    }
    @PutMapping("password")
    public ResponseEntity changePassword(@Valid @RequestBody PasswordRequest passwordRequest) {
        try {
            // Call the service layer to change the password
            authenticationService.changePassword(
                    passwordRequest.getCurrentPassword(),
                    passwordRequest.getNewPassword(),
                    passwordRequest.getConfirmPassword()
            );
            return ResponseEntity.ok("Password updated successfully.");
        } catch (IllegalArgumentException e) {
            // Check if the error is specifically about mismatched passwords
            if (e.getMessage().equals("New password and confirm password do not match.")) {
                return ResponseEntity.badRequest().body("Error: New password and confirm password do not match.");
            } else if (e.getMessage().equals("Current password is incorrect.")) {
                return ResponseEntity.badRequest().body("Error: Current password is incorrect.");
            } else {
                // Handle other potential issues with a generic error message
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } catch (Exception e) {
            // Handle any unexpected errors
            return ResponseEntity.status(500).body("An error occurred while updating the password.");
        }
    }

}
