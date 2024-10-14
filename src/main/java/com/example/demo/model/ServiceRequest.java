package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceRequest {
    @JsonIgnore
    boolean isDeleted = false;

    @NotBlank(message = "Category can't be blank!")
    String name;

    @NotBlank(message = "Category can't be blank!")
    String description;

    String price;

    @NotBlank(message = "Category can't be blank!")
    String duration;

    @NotBlank(message = "Category can't be blank!")
    String category;

    @NotBlank(message = "Category can't be blank!")
    String serviceImage;

    @NotBlank(message = "Category can't be blank!")
    String date;
}
