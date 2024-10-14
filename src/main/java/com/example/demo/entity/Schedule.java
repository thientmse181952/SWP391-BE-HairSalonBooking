package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleID; // Primary key

    @ManyToOne
    @JoinColumn(name = "stylistID", nullable = false)
    private Stylist stylist; // Foreign key to Stylist entity

    @ManyToOne
    @JoinColumn(name = "serviceID", nullable = false)
    private ServiceofHair service; // Foreign key to Service entity

    private String appointmentDate;

    private String startTime;

    private String endTime;

    private String status;
}
