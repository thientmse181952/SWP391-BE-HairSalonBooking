package com.example.demo.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Customer {
    // khoá chính
    @Id // đánh dấu đây là khoá chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // để tự generate ra cột này
            long id;

    boolean isDeleted = false;

    @NotBlank(message = "Name can not be blank!")
    String name;

    @NotBlank
    String Gender;

    @NotBlank(message = "Code can not be blank!")
    @Pattern(regexp = "SE\\d{6}", message = "Student code is not valid!")
    @Column(unique = true) // duy nhất
    String code;

    @Min(value = 0, message = "Score must be at least 0!")
    @Max(value = 10, message = "Score must not be more than 10!")
    float score;
}
