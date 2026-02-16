package com.example.EmployeeManagement.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.example.EmployeeManagement.dto.marker.Request;

/**
 * @author Baxriddinov Odiljon
 * @since 10/02/2026 12:42
 */
public record TokenRequestDTO(
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String password
) implements Request {
}
