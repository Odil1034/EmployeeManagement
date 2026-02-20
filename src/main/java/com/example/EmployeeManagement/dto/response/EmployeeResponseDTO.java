package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

import java.time.LocalDateTime;
import java.util.List;

public record EmployeeResponseDTO(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        LocalDateTime dateOfBirth,
        String gender,
        String address,
        List<String> positionNames
        ) implements Response {
}
