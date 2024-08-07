package com.example.repositoryLayer.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class StudentEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;

    public StudentEntity(String firstName, String lastName, String phone)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public StudentEntity()
    {

    }

}
