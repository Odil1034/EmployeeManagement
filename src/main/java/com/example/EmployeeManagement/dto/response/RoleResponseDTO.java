package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;
import jakarta.validation.constraints.NotNull;

public record RoleResponseDTO(
        @NotNull String name
) implements Response {

}
