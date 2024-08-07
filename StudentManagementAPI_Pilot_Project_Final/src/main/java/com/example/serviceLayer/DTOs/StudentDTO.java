package com.example.serviceLayer.DTOs;

import com.example.validation.ContactNumberConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Integer id;
    @NotNull
    @Size(min = 2, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 100)
    private String lastName;

    @NotNull
    @ContactNumberConstraint
    private String phone;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
