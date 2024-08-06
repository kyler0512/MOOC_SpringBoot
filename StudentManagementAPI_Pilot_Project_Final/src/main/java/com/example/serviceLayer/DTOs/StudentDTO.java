package com.example.serviceLayer.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Integer id;
    @NotNull
    @Size(min = 2, message = "{validation.name.size.too_short}")
    private String firstName;

    @NotNull
    @Size(min = 2, message = "{validation.name.size.too_short}")
    private String lastName;

    @NotNull
    @Size(min = 2, message = "{validation.name.size.too_short}")
    private String phone;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
