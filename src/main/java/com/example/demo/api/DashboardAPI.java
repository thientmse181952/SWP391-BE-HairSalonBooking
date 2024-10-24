package com.example.demo.api;

import com.example.demo.entity.Role;
import com.example.demo.service.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@SecurityRequirement(name = "api")
public class DashboardAPI {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity getDashboardStats() {
        Map<String, Object> stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/revenue/{month}/{year}")
    public ResponseEntity<?> getMonthlyRevenue(@PathVariable int month, @PathVariable int year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng được xác thực có phải là người quản lý không
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.MANAGER.toString()))) {
            Map<String, Object> revenueData = dashboardService.getMonthlyRevenue(month, year);
            return ResponseEntity.ok(revenueData);
        }

        return ResponseEntity.status(403).body("Access denied: You must be a manager to view revenue data.");
    }

}
