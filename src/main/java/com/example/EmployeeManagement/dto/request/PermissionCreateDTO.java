package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record PermissionCreateDTO(
        @NotBlank String access,
        String description,
        Set<Long> roleId
) implements Request {
}
