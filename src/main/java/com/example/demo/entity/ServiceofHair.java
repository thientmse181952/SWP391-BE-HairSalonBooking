package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServiceofHair {
    @JsonIgnore
    boolean isDeleted = false;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "Category can't be blank!")
    String name;

    @NotBlank(message = "Category can't be blank!")
    String description;

    int price;

    @NotBlank(message = "Category can't be blank!")
    String duration;

    @NotBlank(message = "Category can't be blank!")
    String stylist;

    @NotBlank(message = "Category can't be blank!")
    String category;

    @NotBlank(message = "Category can't be blank!")
    String serviceImage;

    @NotBlank(message = "Category can't be blank!")
    String date;

}
