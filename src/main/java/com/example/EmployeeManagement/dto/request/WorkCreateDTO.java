package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// Work — real ishchi tomonidan bajarilgan ish
public record WorkCreateDTO(
        @NotNull(message = "employeeId is required")
        Long employeeId,
        @NotNull(message = "workTypeId is required")
        Long workTypeId,
        @NotNull(message = "work date is required")
        LocalDate workDate,
        @NotNull(message = "quantity is required")
        Integer quantity
) implements Request {

}

