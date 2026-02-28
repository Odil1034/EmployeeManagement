package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

public record SimpleWorkCategoryDTO(
        Long id,
        String name
) implements Response {
}
