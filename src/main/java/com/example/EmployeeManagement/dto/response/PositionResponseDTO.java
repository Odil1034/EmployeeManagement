package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

import java.util.List;

public record PositionResponseDTO(
        Long id,
        String name,
        String description,
        List<SimpleEmployeeDTO> employees,
        List<SimpleWorkCategoryDTO> allowedWorks
) implements Response {
}
