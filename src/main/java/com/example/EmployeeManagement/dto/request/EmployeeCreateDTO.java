package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import com.example.EmployeeManagement.enums.EmployeeRole;
import com.example.EmployeeManagement.enums.Gender;
import com.example.EmployeeManagement.validation.ValidAge;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EmployeeCreateDTO(
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
        @NotNull(message = "Date of birth is required")
        @ValidAge(min = 18, max = 65)
        LocalDate dateOfBirth,
        @NotNull(message = "Gender is required")
        Gender gender,
        @NotBlank(message = "Address is required")
        String address,
        @NotNull(message = "EmployeeRole is required")
        EmployeeRole employeeRole,
        @NotNull(message = "Department is required")
        Long departmentId
) implements Request {
}