package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

public record DashboardAttendanceDTO(
        long presentToday,
        long lateToday,
        long absentToday
) implements Response {
}
