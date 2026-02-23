package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import com.example.EmployeeManagement.enums.AttendanceStatus;
import com.example.EmployeeManagement.enums.CheckInStatus;

import java.time.LocalDate;

public record AttendanceFilterDTO(
        Long employeeId,
        Long departmentId,
        AttendanceStatus status,
        CheckInStatus checkInStatus,
        LocalDate from,
        LocalDate to
) implements Request {
}
