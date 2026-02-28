package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

import java.util.List;

public record WorkCategoryResponseDTO(
        Long id,
        String name,
        String description,
        SimplePositionDTO allowedPosition,
        List<SimpleWorkTypeDTO> workTypes
) implements Response {
}
