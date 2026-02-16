package com.example.EmployeeManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.example.EmployeeManagement.dto.marker.Request;

/**
 * @author Baxriddinov Odiljon
 * @since 08/02/2026 08:33
 */
public record UserCreateDTO(
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String password,
        String firstName,
        String lastName,
        String phoneNumber
) implements Request {

}
