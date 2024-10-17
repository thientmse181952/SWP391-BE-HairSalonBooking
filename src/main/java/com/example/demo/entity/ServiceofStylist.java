package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ServiceofStylist {
    @JsonIgnore
    boolean isDeleted = false;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "Category can't be blank!")
    String name;

    @NotBlank(message = "Category can't be blank!")
    String description;

    String price;

    @NotBlank(message = "Category can't be blank!")
    String duration;


    @NotBlank(message = "Category can't be blank!")
    String serviceImage;

    @NotBlank(message = "Category can't be blank!")
    String date;

    @ManyToMany(mappedBy = "serviceofStylists")
    Set<Stylist> stylists;

    @ManyToMany(mappedBy = "serviceofStylists")
    Set<Booking> bookings;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    Category category;



}
