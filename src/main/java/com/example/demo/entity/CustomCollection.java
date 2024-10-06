package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @NotBlank(message = "Category can't be blank!")
    String category;

    @NotBlank(message = "CollectionImage can't be blank!")
    String collectionImage;

    @NotBlank(message = "Category can't be blank!")
    String date;

    String type;
}
