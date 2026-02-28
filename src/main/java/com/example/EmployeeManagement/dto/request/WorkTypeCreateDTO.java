package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotNull;

public record WorkTypeCreateDTO(
        @NotNull(message = "name is required")
        String name, // Размерга кесиш, Тешиш, Овал, Сайкаллаш
        @NotNull(message = "work category is required")
        Long workCategoryId,
        String defaultPrice // optional
) implements Request {
}