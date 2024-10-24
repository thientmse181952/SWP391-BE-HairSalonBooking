package com.example.demo.repository;

import com.example.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b.stylist, COUNT(b) as bookingCount FROM Booking b " +
            "GROUP BY b.stylist " +
            "ORDER BY bookingCount DESC")
    List<Object[]> findTop3Stylists(Pageable pageable);

}
