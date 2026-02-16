package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author Baxriddinov Odiljon
 * @since 08/02/2026 08:35
 */
public record UserResponseDTO(
        @NotNull Long id,
        String firstName,
        String lastName,
        @NotNull String username,
        String phoneNumber,
        String email,
        LocalDate dateOfBirth,
        @NotNull Set<String> roles,
        @NotNull boolean isAccountNonLocked
        ) implements Response {

}
