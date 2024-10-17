package com.example.demo.model;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Stylist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {
    @JsonIgnore
    boolean isDeleted = false;

    List<Long> service_id;

    @ManyToOne
    @JoinColumn(name = "stylist_id", nullable = false)
    Stylist stylist;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;
    String appointmentDate;
    String startTime;
    String endTime;
    String status;
}
