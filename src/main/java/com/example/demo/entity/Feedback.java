package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    boolean isDeleted = false;

    String rating_stylist;

    String comment;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    Booking booking;


   }