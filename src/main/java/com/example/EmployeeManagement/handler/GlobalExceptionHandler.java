package com.example.EmployeeManagement.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.EmployeeManagement.dto.ErrorResponse;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.exception.BaseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Response<ErrorResponse>> handleCryptoOperationException(BaseException ex, HttpServletRequest request) {
        var errorResponse = ErrorResponse.of(ex.getCode(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(Response.error(
                ex.getHttpStatus().value(),
                errorResponse
        ), ex.getHttpStatus());
    }
}
