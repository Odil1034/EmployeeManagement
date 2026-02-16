package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotNull;

public record PermissionUpdateDTO(
        @NotNull Long roleId,
        @NotNull String oldPermissionName,
        @NotNull String newPermissionName,
        String description
) implements Request {
}
