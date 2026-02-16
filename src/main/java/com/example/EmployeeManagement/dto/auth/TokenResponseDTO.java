package com.example.EmployeeManagement.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import com.example.EmployeeManagement.dto.marker.Response;
import com.example.EmployeeManagement.enums.TokenType;

/**
 * * @author Odiljon Baxriddinov
 * * @since 10/02/2026 12:42
 */
@Validated
@JsonPropertyOrder({"user_id", "token_type", "access", "refresh", "_links"})
public record TokenResponseDTO(
        @JsonProperty("token_type")
        @NotNull TokenType tokenType,
        @JsonProperty("user_id")
        @NotNull Long userId,
        @NotNull TokenDTO access,
        @NotNull TokenDTO refresh
) implements Response {
    public static TokenResponseDTO of(Long userId, TokenDTO access, TokenDTO refresh) {
        return new TokenResponseDTO(TokenType.BEARER, userId, access, refresh);
    }
}

