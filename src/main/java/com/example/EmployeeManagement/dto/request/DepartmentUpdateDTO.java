package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;

public record DepartmentUpdateDTO(
        String name,
        String address
) implements Request {
}
