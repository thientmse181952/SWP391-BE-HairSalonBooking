package com.example.demo.repository;


import com.example.demo.entity.ServiceofStylist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceofStylistRepository extends JpaRepository<ServiceofStylist, Long> {
    @Query("SELECT COUNT(s) FROM ServiceofStylist s WHERE s.isDeleted = false")
    long countAllServicesOfStylist();
    @Query("SELECT s, COUNT(b.id) as bookingCount FROM ServiceofStylist s " +
            "JOIN s.bookings b " +
            "GROUP BY s.id " +
            "ORDER BY bookingCount DESC")
    List<Object[]> findTop5MostBookedServices(Pageable pageable);
}
