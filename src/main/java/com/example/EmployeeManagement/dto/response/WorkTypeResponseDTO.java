package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;

import java.math.BigDecimal;

public record WorkTypeResponseDTO(
        Long id,
        String name,
        Long workCategoryId,
        String workCategoryName,
        BigDecimal defaultPrice
) implements Response {
}
