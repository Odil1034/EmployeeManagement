package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;
import com.example.EmployeeManagement.enums.EmployeeRole;
import com.example.EmployeeManagement.enums.EmployeeStatus;
import com.example.EmployeeManagement.enums.Gender;

import java.time.LocalDate;
import java.util.List;

public record EmployeeResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        LocalDate dateOfBirth,
        Gender gender,
        String address,
        EmployeeStatus status,
        EmployeeRole employeeRole,
        Long departmentId,
        String departmentName,
        List<String> positionNames
        ) implements Response {
}
