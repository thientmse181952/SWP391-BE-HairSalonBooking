//package com.example.demo.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.NotBlank;
//import lombok.Getter;
//import lombok.Setter;
//
//@Setter
//@Getter
//@Entity
//public class CategoryCollection {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    long id;
//
//    @JsonIgnore // kh trả về và kh bắt user nhập thông tin
//    boolean isDeleted = false; //false = not deleted
//
//    @NotBlank(message = "Name can not be blank!")
//    String nameCategory;
//
//    String type;
//}
