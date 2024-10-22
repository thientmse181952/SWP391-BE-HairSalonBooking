package com.example.demo.model;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Stylist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleRequest {
    @JsonIgnore // kh trả về và kh bắt user nhập thông tin
    boolean isDeleted = false; //false = not deleted


    Stylist stylist_id;
    String reason;
    String status;
    String startTime;
    String endTime;






}
