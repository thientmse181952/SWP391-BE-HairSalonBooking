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

    @ManyToOne
    @JoinColumn(name = "stylist_id", nullable = false)
    Stylist stylist;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;



   }