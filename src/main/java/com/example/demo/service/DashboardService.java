package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.entity.Stylist;
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
        Pageable topThree = PageRequest.of(0, 3);
        List<Object[]> topStylists = bookingRepository.findTop3Stylists(topThree);
        List<Map<String, Object>> topStylistData = new ArrayList<>();
        for (Object[] result : topStylists) {
            Stylist stylist = (Stylist) result[0];
            long bookingCount = (long) result[1];

            Map<String, Object> stylistData = new HashMap<>();
            stylistData.put("stylistName", stylist.getAccount().getFullName());
            stylistData.put("stylistId", stylist.getId());
            stylistData.put("bookingCount", bookingCount);

            topStylistData.add(stylistData);
        }
        stats.put("topStylists", topStylistData);

        return stats;
    }
}
