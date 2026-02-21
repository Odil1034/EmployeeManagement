package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import com.example.EmployeeManagement.enums.AttendanceStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record AttendanceCreateDTO(
        Long shiftAssignmentId,
        LocalTime checkIn,
        LocalTime checkOut,
        AttendanceStatus status,
        Long approvedBy,
        LocalDateTime approvedAt,
        boolean isApproved
) implements Request {
}
