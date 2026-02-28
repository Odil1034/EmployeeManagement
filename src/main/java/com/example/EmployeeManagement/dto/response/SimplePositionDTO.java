package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

public record SimplePositionDTO(
        Long id,
        String name,
        String description
) implements Response {
}
