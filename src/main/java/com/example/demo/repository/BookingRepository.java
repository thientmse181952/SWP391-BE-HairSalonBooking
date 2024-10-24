package com.example.demo.repository;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Stylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerId(Long customerId);

    @Query("SELECT b.stylist, COUNT(b) as bookingCount FROM Booking b " +
            "GROUP BY b.stylist " +
            "ORDER BY bookingCount DESC")
    List<Object[]> findTop3Stylists(Pageable pageable);

    @Query("SELECT c FROM Booking b JOIN b.customer c")
    List<Customer> findAllCustomers();

    @Query("SELECT s FROM Booking b JOIN b.stylist s")
    List<Stylist> findAllStylists();

}
