package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotNull;

public record PositionCreateDTO(
        @NotNull(message = "name is required")
        String name,
        String description
//        @NotNull(message = "allowed work categories is required")
//        List<Long> allowedWorkCategoryIds
) implements Request {
}
