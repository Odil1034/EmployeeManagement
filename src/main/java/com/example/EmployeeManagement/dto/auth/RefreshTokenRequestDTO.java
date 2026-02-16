package com.example.EmployeeManagement.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.example.EmployeeManagement.dto.marker.Request;

/**
 * @author Baxriddinov Odiljon
 * @since 10/02/2026 12:42
 */
public record RefreshTokenRequestDTO(
        @JsonProperty("refresh_token")
        @NotBlank @NotNull String refreshToken
) implements Request {
}
