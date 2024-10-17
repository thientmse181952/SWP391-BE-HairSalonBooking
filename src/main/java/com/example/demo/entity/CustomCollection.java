package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class CustomCollection {
    @JsonIgnore
    boolean isDeleted = false;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    @NotBlank(message = "CollectionImage can't be blank!")
    String collectionImage;

    @NotBlank(message = "Category can't be blank!")
    String date;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    Category category;

}
