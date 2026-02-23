package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import com.example.EmployeeManagement.enums.AttendanceStatus;

import java.time.LocalTime;

/**
 * DTO for updating Attendance
 */
public record AttendanceUpdateDTO(
        AttendanceStatus status,
        LocalTime checkIn,
        LocalTime checkOut
) implements Request {
}