package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotNull;

public record ActivateShiftDTO(
        @NotNull Boolean active
) implements Request {
}
