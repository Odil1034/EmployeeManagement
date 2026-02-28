package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

public record SimpleWorkTypeDTO(
        Long id,
        String name,
        String basePrice
) implements Response {
}
