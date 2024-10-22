package com.example.demo.model;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Schedule;
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

    Stylist stylist_id;

    Customer customer_id;

    String appointmentDate;
    String startTime;
    String endTime;
    String status;
}
