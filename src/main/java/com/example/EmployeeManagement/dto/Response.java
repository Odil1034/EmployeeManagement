package com.example.EmployeeManagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import com.example.EmployeeManagement.dto.marker.DTO;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements DTO {

    private boolean success;
    private int status;
    private String message;
    private T data;
    private ErrorResponse error;

    public static <T> Response<T> ok(T data) {
        return new Response<>(true, 200, null, data, null);
    }

    public static <T> Response<T> ok(int status, T data) {
        return new Response<>(true, status, null, data, null);
    }

    public static <T> Response<T> ok(HttpStatus status, T data) {
        return new Response<>(true, status.value(), null, data, null);
    }

    public static <T> Response<T> ok(int status, T data, String message) {
        return new Response<>(true, status, message, data, null);
    }

    public static <T> Response<T> error(int status, ErrorResponse error) {
        return new Response<>(false, status, null, null, error);
    }

    public static <T> Response<T> error(HttpStatus status, ErrorResponse error) {
        return new Response<>(false, status.value(), null, null, error);
    }

    public Response(boolean success, int status, String message, T data, ErrorResponse error) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }
}
