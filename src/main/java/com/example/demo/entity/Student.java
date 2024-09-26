package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Student {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank(message = "Name can't be blank!")
    String name;
    @NotBlank(message = "Code can't be blank!")
    @Pattern(regexp = "SE\\d{6}", message = "Student code is not valid!")
    String code;

    @Min(value=0, message = "Score must be at least 0!")
    @Max(value = 10, message = "Score must not be more than 10!")
    float score;
}

