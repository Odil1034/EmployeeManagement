package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

import java.time.LocalTime;

public record SimpleShiftDTO(
        Long id,
        String name,
        LocalTime startTime,
        LocalTime endTime
) implements Response {
}
