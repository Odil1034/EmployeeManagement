package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;
import com.example.EmployeeManagement.enums.ShiftType;

import java.time.LocalTime;

public record ShiftResponseDTO(
        Long id,
        ShiftType name,
        LocalTime startTime,
        LocalTime endTime,
        Integer gracePeriodMinutes,
        Boolean active
) implements Response {
}
