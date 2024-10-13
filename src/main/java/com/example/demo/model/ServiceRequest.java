package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class ServiceRequest {
    @NotBlank(message = "Name can not be blank!")
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
