package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Account {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank(message = "Code can't be blank!")
    @Pattern(regexp = "KH\\d{6}", message = "Invalid code!")
    @Column(unique = true)
    String code;
    @Email(message = "Invaild email")
    @Column(unique = true)
    String email;
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone!")
    @Column(unique = true)
    String phone;
    @Size(min = 6, message = "Password must be at least 6 character!")
    String password;

}
