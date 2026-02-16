package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Request;

import java.util.Set;

public record RoleUpdateDTO(
        String name,
        String description,
        Set<Long> permissionIds
) implements Request {
}
