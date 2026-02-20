package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotBlank;

public record DepartmentCreateDTO(
        @NotBlank String name,
        @NotBlank String address
) implements Request {
}
