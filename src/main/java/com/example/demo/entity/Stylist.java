package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
public class Stylist {
    // khoá chính
    @Id // đánh dấu đây là khoá chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // để tự generate ra cột này
            long id;
    @JsonIgnore // kh trả về và kh bắt user nhập thông tin
    boolean isDeleted = false; //false = not deleted

    @NotBlank(message = "Name can not be blank!")
    String name;

    String rating;

    String image;

    @NotBlank(message = "Code can not be blank!")
    @Pattern(regexp = "^(Male|Female)$", message = ("Invalid Gender"))
    String Gender;

   @ManyToMany
           @JoinTable(name = "service_class",
                   joinColumns = @JoinColumn(name = "stylist_id"),
                   inverseJoinColumns = @JoinColumn(name = "serviceofStylist_id")


           )

@JsonIgnore
   Set<ServiceofStylist> serviceofStylists;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

}
