package com.example.EmployeeManagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.Pattern;

/**
 * @author Baxriddinov Odiljon
 * @since 08/02/2026 08:33
 */
public record UserCreateDTO(
        @NotNull(message = "username is required")
        String username,

        @NotNull(message = "password is required")
        String password,

        @Email(message = "Email should be valid")
        @NotNull(message = "email is required")
        String email,

        @Pattern(regexp = "^\\+998\\d{9}$", message = "Phone number must be +998XXXXXXXXX")
        String phoneNumber
) implements Request {

}
