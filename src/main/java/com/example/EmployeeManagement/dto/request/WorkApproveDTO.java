package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;

import java.math.BigDecimal;

public record WorkApproveDTO(
        BigDecimal price // optional override
) implements Request {
}
