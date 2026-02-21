package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import com.example.EmployeeManagement.enums.ShiftType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ShiftCreateDTO(
        @NotNull(message = "Shift type is required")
        ShiftType name,
        @NotNull(message = "Shift start time is required")
        LocalTime startTime,
        @NotNull(message = "Shift end time is required")
        LocalTime endTime,
        Integer gracePeriodMinutes, // optional, default service layer’da set qilamiz
        Boolean active // optional, default true
) implements Request {

}

