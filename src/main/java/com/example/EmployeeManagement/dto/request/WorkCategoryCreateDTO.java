package com.example.EmployeeManagement.dto.request;

import com.example.EmployeeManagement.dto.marker.Request;
import jakarta.validation.constraints.NotNull;

public record WorkCategoryCreateDTO(
        @NotNull(message = "name is required")
        String name, // Yog‘ochga ishlov berish, MDFga ishlov berish, Temir mahsulotlari
        String description // texnika xavfsizlik yoki guideline(ishni bajarish tartibi)
) implements Request {
}
