package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChangInforRequest  {
    @NotBlank
    String fullName;

    @NotBlank(message = "Code can not be blank!")
    @Pattern(regexp = "^(Male|Female)$", message = ("Invalid Gender"))
    String Gender;

    @Email(message = "Invalid Email!")
    @Column(unique = true)
    String email;
}
