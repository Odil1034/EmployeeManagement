package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;

public record WorkTypeUpdateDTO(
        String name, // Размерга кесиш, Тешиш, Овал, Сайкаллаш
        Long workCategoryId,
        String defaultPrice // optional
) implements Request {
}