package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserUpdateDTO(
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
