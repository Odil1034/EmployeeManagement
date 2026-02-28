package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PositionUpdateDTO(
        String name,
        String description
//        List<Long> allowedWorkCategoryIds
) implements Request {
}


