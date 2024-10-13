package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "serviceofHair_id", nullable = false)
    ServiceofStylist serviceofHair;

   String appointmentDate;
   String startTime;
   String endTime;
   String status;
   }