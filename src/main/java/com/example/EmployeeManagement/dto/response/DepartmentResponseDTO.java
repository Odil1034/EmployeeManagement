package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

public record DepartmentResponseDTO(
        String name,
        String address,
        String status
) implements Response {

}
