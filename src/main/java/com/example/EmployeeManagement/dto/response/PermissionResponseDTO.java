package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;
import jakarta.validation.constraints.NotNull;

public record PermissionResponseDTO(
        @NotNull String name,
        @NotNull String description
) implements Response {

}