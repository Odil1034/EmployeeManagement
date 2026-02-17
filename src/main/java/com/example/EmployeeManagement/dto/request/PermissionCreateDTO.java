package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotBlank;

public record PermissionCreateDTO(
        @NotBlank String access,
        String description
) implements Request {
}
