package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @JsonIgnore // kh trả về và kh bắt user nhập thông tin
    boolean isDeleted = false; //false = not deleted

    @NotBlank(message = "Name can not be blank!")
    String name;

    @Email(message = "Invalid Email!")
    @Column(unique = true)
    String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone!")
    @Column(unique = true)
    String phone;

    @NotBlank(message = "Code can not be blank!")
    @Pattern(regexp = "^(Male|Female)$", message = ("Invalid Gender"))
    String Gender;

//    @OneToOne
//    @JoinColumn(name = "account_id")
//    private Account account;




}
