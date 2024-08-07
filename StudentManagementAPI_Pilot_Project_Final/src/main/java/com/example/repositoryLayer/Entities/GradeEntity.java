package com.example.repositoryLayer.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Double score;
    private Integer studentId;

    public GradeEntity(String name, double score, int studentId) {
        this.name = name;
        this.score = score;
        this.studentId = studentId;
    }
}
