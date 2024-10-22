package com.example.demo.model;

import com.example.demo.entity.Account;
import com.example.demo.entity.Category;
import com.example.demo.entity.ServiceofStylist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class StylistRequest {
    @JsonIgnore // kh trả về và kh bắt user nhập thông tin
    boolean isDeleted = false; //false = not deleted


    String image;

    List<Long> service_id;



}
