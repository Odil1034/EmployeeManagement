package com.example.EmployeeManagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.example.EmployeeManagement.dto.marker.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String code,
        String message,
        String path,
        String details,
        LocalDateTime timestamp) implements Response {


    public static ErrorResponse of(String code, String message, String path) {
        return new ErrorResponse(
                code,
                message,
                path,
                "Contact support for more details.",
                LocalDateTime.now()
        );
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

}
