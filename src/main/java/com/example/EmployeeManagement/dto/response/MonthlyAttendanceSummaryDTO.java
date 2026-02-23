package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

public record MonthlyAttendanceSummaryDTO(
        Long total,
        Long present,
        Long late,
        Long absent
) implements Response {
}
