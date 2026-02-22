package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;
import com.example.EmployeeManagement.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AttendanceResponseDTO(
        Long id,
        Long employeeId,
        String employeeFullName,
        Long shiftAssignmentId,
        Long shiftId,
        String shiftName,
        LocalTime checkIn,
        LocalTime checkOut,
        AttendanceStatus status,
        Boolean isApproved,
        Long approvedBy,
        LocalDateTime approvedAt,
        LocalDate date
) implements Response {
}
