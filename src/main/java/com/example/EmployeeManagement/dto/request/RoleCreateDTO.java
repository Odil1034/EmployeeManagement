package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotBlank;

public record RoleCreateDTO(
        @NotBlank String name,
        String description
) implements Request {
}
