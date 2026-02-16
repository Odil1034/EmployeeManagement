package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

import java.util.Set;

public record RolePermissionsDTO(
        String roleName,
        Set<PermissionResponseDTO> permissions
) implements Response {

}
