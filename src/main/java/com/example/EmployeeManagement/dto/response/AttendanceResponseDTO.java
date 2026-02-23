package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;
import com.example.EmployeeManagement.enums.AttendanceStatus;
import com.example.EmployeeManagement.enums.CheckInStatus;
import com.example.EmployeeManagement.enums.CheckOutStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonPropertyOrder({
        "id",
        "employeeId",
        "employeeFullName",
        "shiftAssignmentId",
        "shiftId",
        "shiftName",
        "checkIn",
        "checkInStatus",
        "checkOut",
        "checkOutStatus",
        "status",
        "isApproved",
        "approvedBy",
        "approvedAt",
        "date"
})
public record AttendanceResponseDTO(
        Long id,
        Long employeeId,
        String employeeFullName,
        Long shiftAssignmentId,
        Long shiftId,
        String shiftName,
        LocalTime checkIn,
        CheckInStatus checkInStatus,
        LocalTime checkOut,
        CheckOutStatus checkOutStatus,
        AttendanceStatus status,
        Boolean isApproved,
        Long approvedBy,
        LocalDateTime approvedAt,
        LocalDate date
) implements Response {
}
