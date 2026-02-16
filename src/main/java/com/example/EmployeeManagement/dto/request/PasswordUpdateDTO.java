package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PasswordUpdateDTO(
        @NotBlank @NotNull String oldPassword,
        @NotBlank @NotNull String newPassword
) implements Request {
}