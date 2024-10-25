package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id; // Primary key

    @JsonIgnore
    boolean isDeleted = false;

    String reason;
    String status;
    String startTime;
    String endTime;
    @ManyToOne
    @JoinColumn(name = "stylist_id")
    Stylist stylist;


}
