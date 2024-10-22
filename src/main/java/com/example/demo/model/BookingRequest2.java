package com.example.demo.model;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Stylist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest2 {
    @JsonIgnore
    boolean isDeleted = false;


    Stylist stylist_id;

}
