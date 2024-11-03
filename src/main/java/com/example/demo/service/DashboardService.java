package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ServiceofStylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ServiceofStylistRepository serviceofStylistRepository;

    @Autowired
    PaymentRepository paymentRepository;

    public List<Map<String, String>> getAllCustomers() {
        List<Customer> customers = bookingRepository.findAllCustomers();

        List<Map<String, String>> customerData = new ArrayList<>();
        for (Customer customer : customers) {
            Map<String, String> data = new HashMap<>();
            data.put("fullName", customer.getAccount().getFullName()); // Adjust according to your method for getting the full name
            customerData.add(data);
        }

        return customerData;
    }

    public List<Map<String, String>> getAllStylists() {
        List<Stylist> stylists = bookingRepository.findAllStylists();

        List<Map<String, String>> stylistData = new ArrayList<>();
        for (Stylist stylist : stylists) {
            Map<String, String> data = new HashMap<>();
            data.put("fullName", stylist.getAccount().getFullName()); // Adjust according to your method for getting the full name
            stylistData.add(data);
        }

        return stylistData;
    }
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

        //số lượng dịch vụ
        long serviceofStylistCount = serviceofStylistRepository.countAllServicesOfStylist();
        stats.put("serviceofstylistcount", serviceofStylistCount);

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

        // Top 5 dịch vụ được book nhèo nhất
        Pageable topFive = PageRequest.of(0, 5);
        List<Object[]> topServices = serviceofStylistRepository.findTop5MostBookedServices(topFive);

        List<Map<String, Object>> topServiceData = topServices.stream().map(service -> {
            ServiceofStylist serviceofStylist = (ServiceofStylist) service[0];
            long bookingCount = (long) service[1];

            Map<String, Object> serviceMap = new HashMap<>();
            serviceMap.put("serviceId", serviceofStylist.getId());
            serviceMap.put("serviceName", serviceofStylist.getName());
            serviceMap.put("bookingCount", bookingCount);

            return serviceMap;
        }).toList();

        stats.put("top5services", topServiceData);

        return stats;
    }

    //Doanh thu
    public Map<String, Object> getMonthlyRevenue(int month, int year) {
        Map<String, Object> revenueData = new HashMap<>();

        // Doanh thu cho tháng và năm được chỉ định
        List<Payment> payments = paymentRepository.findPaymentsByMonthAndYear(month, year);

        // Tính tổng doanh thu tháng
        BigDecimal totalMonthlyRevenue = BigDecimal.ZERO;

        for (Payment payment : payments) {
            // Chuyển amount thành BigDecimal
            try {
                totalMonthlyRevenue = totalMonthlyRevenue.add(new BigDecimal(payment.getAmount()));
            } catch (NumberFormatException e) {
                // Xử lý trường hợp số tiền không phải là số hợp lệ
                e.printStackTrace(); // Ghi lại thay vì in ra
            }
        }

        // Lưu trữ kết quả ở Map
        revenueData.put("month", month);
        revenueData.put("year", year);
        revenueData.put("totalRevenue", totalMonthlyRevenue.toString()); // Chuyển đổi thành String nếu cần

        return revenueData;
    }

    public Map<String, Object> getMonthsWithRevenue() {
        Map<String, Object> revenueMonthsData = new HashMap<>();
        List<Object[]> monthsWithRevenue = paymentRepository.findDistinctMonthsAndYears();

        Set<String> uniqueMonths = new HashSet<>();
        List<Map<String, Object>> months = new ArrayList<>();

        for (Object[] entry : monthsWithRevenue) {
            int month = (int) entry[0];
            int year = (int) entry[1];
            String monthYearKey = month + "-" + year;

            // Ensure the month is only processed once
            if (!uniqueMonths.contains(monthYearKey)) {
                uniqueMonths.add(monthYearKey);

                // Calculate revenue for this month and year
                Map<String, Object> revenueData = getMonthlyRevenue(month, year);

                Map<String, Object> monthData = new HashMap<>();
                monthData.put("month", month);
                monthData.put("year", year);
                monthData.put("totalRevenue", revenueData.get("totalRevenue"));

                months.add(monthData);
            }
        }

        revenueMonthsData.put("monthsWithRevenue", months);
        return revenueMonthsData;
    }
}

