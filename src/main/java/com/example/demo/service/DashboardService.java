package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AccountRepository accountRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        //đếm số lượng booking trong hệ thống
        long totalBookings = bookingRepository.count();
        stats.put("totalbookings", totalBookings);

        //số lượng cus
        long customerCount = accountRepository.countByRole(Role.CUSTOMER);
        stats.put("customercount", customerCount);

        //số lượng stylist
        long stylistCount = accountRepository.countByRole(Role.STYLIST);
        stats.put("stylistcount", stylistCount);

        //top 3 stylist
        Pageable top3 = (Pageable) PageRequest.of(0, 3); // Limit the query to 3 results
        List<Object[]> topStylists = bookingRepository.findTopStylistsByBookings(top3);

        List<Map<String, Object>> topStylistList = new ArrayList<>();
        for (Object[] result : topStylists) {
            Map<String, Object> stylistInfo = new HashMap<>();
            Long stylistId = (Long) result[0]; // Stylist ID
            Long bookingCount = (Long) result[1]; // Booking count

            // Fetch stylist details from AccountRepository
            Account stylistAccount = accountRepository.findById(stylistId).orElse(null);
            if (stylistAccount != null) {
                stylistInfo.put("stylistname", stylistAccount.getFullName());
                stylistInfo.put("stylistbooked", bookingCount);
                topStylistList.add(stylistInfo);
            }
        }

        // Add the top stylists to the stats
        stats.put("topstylists", topStylistList);

        return stats;
    }
}
