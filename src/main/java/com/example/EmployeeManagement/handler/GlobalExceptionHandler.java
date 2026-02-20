package com.example.EmployeeManagement.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.EmployeeManagement.dto.ErrorResponse;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.exception.BaseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<Response<ErrorResponse>> handleCryptoOperationException(BaseException ex, HttpServletRequest request) {
        var errorResponse = ErrorResponse.of(ex.getCode(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(Response.error(
                ex.getHttpStatus().value(),
                errorResponse
        ), ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<ErrorResponse>> handleAll(Exception ex, HttpServletRequest request) {
        HttpStatus status;

        if (ex instanceof org.springframework.dao.DataIntegrityViolationException) {
            status = HttpStatus.CONFLICT; // duplicate key, constraint violation
        } else if (ex instanceof IllegalArgumentException || ex instanceof jakarta.validation.ValidationException) {
            status = HttpStatus.BAD_REQUEST; // invalid input
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR; // unknown
        }

        var errorResponse = ErrorResponse.of(status.name(), ex.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(Response.error(status, errorResponse), status);
    }
}
