package com.example.serviceLayer.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeDTO {
    private Integer id;

    @NotNull @Size(min = 2, max = 100)
    private String name;

    @NotNull @Positive
    private Double score;

    @NotNull
    private Integer studentId;
}
