package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long feedbackId;

    String rating_stylist;

    String comment;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    Booking booking;


   }