package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long paymentId;
    @JsonIgnore
    boolean isDeleted = false;

    String amount;
    String payment_date;
    String payment_type;

    @ManyToMany
    @JoinTable(name = "Invoice",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")


    )
    @JsonIgnore
    Set<Booking> bookings;




   }