package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;

import java.time.LocalDate;

public record ShiftAssignmentUpdateDTO(
        Long employeeId,
        Long shiftId,
        LocalDate workDate
) implements Request {
}
