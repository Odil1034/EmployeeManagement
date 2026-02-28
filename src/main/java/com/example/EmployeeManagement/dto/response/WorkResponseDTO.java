package com.example.EmployeeManagement.dto.response;

import com.example.EmployeeManagement.dto.marker.Response;
import com.example.EmployeeManagement.enums.WorkStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record WorkResponseDTO(
        Long id,

        Long employeeId,
        String employeeName,

        Long workTypeId,
        String workTypeName,

        LocalDate workDate,

        Long productId,
        String productName,

        Integer quantity,

        BigDecimal price,

        BigDecimal totalAmount,

        WorkStatus status,

        String approvedByName // faqat ism,
) implements Response {
}
