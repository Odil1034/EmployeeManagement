package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

import java.time.LocalDate;

public record ShiftAssignmentResponseDTO(
        Long id,
        SimpleEmployeeDTO employee,
        SimpleShiftDTO shift,
        LocalDate workDate
) implements Response {
}
