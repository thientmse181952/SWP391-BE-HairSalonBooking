package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long bookingId;

    @ManyToOne
    @JoinColumn(name = "stylist_id", nullable = false)
    Stylist stylist;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;


    @ManyToMany
    @JoinTable(name = "booking_class",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")


    )
    @JsonIgnore
    Set<ServiceofStylist> serviceofStylists;


    @ManyToMany(mappedBy = "bookings")
    Set<Payment> payments;



   String appointmentDate;
   String startTime;
   String endTime;
   String status;


   }