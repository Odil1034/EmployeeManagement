package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

public record SimpleEmployeeDTO(
        Long id,
        String fullName
) implements Response {
}
