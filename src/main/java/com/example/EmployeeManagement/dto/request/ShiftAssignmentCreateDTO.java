package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ShiftAssignmentCreateDTO(
        @NotNull Long employeeId,
        @NotNull Long shiftId,
        @NotNull @FutureOrPresent
        LocalDate workDate
) implements Request {
}
