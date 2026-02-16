package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotNull;

public record PermissionUpdateDTO(
        @NotNull String access,
        String description
) implements Request {
}
