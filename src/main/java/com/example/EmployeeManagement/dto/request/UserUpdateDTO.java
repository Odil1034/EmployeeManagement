package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserUpdateDTO(
        String firstName,
        String lastName,
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String phoneNumber,
        @NotBlank @NotNull String email,
        @NotBlank @NotNull LocalDate dateOfBirth

) implements Request {

}
