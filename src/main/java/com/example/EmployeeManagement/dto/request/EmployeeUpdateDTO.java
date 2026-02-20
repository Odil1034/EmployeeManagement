package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import com.example.EmployeeManagement.enums.EmployeeRole;
import com.example.EmployeeManagement.enums.Gender;
import com.example.EmployeeManagement.validation.LocalDateDeserializer;
import com.example.EmployeeManagement.validation.ValidAge;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeUpdateDTO(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+998\\d{9}$",
                message = "Phone number must be in format +998XXXXXXXXX")
        String phoneNumber,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @NotNull(message = "Date of birth is required")
        @ValidAge(min = 18, max = 65)
        LocalDate dateOfBirth,
        @NotNull(message = "Gender is required")
        Gender gender,
        @NotBlank(message = "Address is required")
        String address,
        @NotNull(message = "EmployeeRole is required")
        EmployeeRole employeeRole,
        @NotNull(message = "Department id is required")
        Long departmentId
) implements Request {

}
