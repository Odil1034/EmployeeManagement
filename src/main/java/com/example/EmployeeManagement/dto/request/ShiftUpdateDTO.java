package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import com.example.EmployeeManagement.enums.ShiftType;

import java.time.LocalTime;

public record ShiftUpdateDTO(
        ShiftType name,
        LocalTime startTime,
        LocalTime endTime,
        Integer gracePeriodMinutes,
        Boolean active
) implements Request {
}

