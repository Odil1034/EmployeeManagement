package com.example.EmployeeManagement.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.example.EmployeeManagement.dto.marker.Response;

import java.time.LocalDateTime;

/**
 * @author Baxriddinov Odiljon
 * @since 10/02/2026 12:42
 */
@JsonPropertyOrder({"token", "issuedAt", "expiredAt", "expiresIn"})
public record TokenDTO(
        @NotBlank @NotNull String token,
        @JsonProperty("issued_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        @NotNull LocalDateTime issuedAt,

        @JsonProperty("expired_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        @NotNull LocalDateTime expiredAt,

        @JsonProperty("expires_in")
        @NotNull Long expiresIn
) implements Response {
}
