package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;
import jakarta.validation.constraints.NotNull;

/**
 * @author Baxriddinov Odiljon
 * @since 08/02/2026 08:35
 */
public record UserResponseDTO(
        @NotNull Long id,
        @NotNull String username,
        String phoneNumber,
        String email,
//        @NotNull Set<String> roles,
//        @NotNull Set<Long> permissionIds,
        @NotNull boolean isAccountNonLocked
) implements Response {

}
