package com.example.EmployeeManagement.dto.response;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserDTO(
        @NotNull Long id,
        @NotNull String username,
        @NotNull Set<String> roles
//        @NotNull Set<String> permissionIds
) {

}
