package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotNull;

public record AttendanceCheckOutDTO(
        @NotNull(message = "Shift Assignment ID is required")
        Long shiftAssignmentId
) implements Request {
}
