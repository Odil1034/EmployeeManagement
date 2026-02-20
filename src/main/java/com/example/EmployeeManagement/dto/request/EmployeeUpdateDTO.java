package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import com.example.EmployeeManagement.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EmployeeUpdateDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String phoneNumber,
        String email,
        @NotBlank LocalDateTime dateOfBirth,
        @NotBlank Gender gender,
        @NotBlank String address
) implements Request {

}
