package com.example.demo.model;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Stylist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {

    String amount;
    String payment_date;
    String payment_type;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @JsonIgnore
    boolean isDeleted = false;

    List<Long> booking_id;

}
