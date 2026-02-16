package com.example.EmployeeManagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Baxriddinov Odiljon
 * @since 11/February/2026  15:19
 **/
@Getter
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message, Object... args) {
        super(HttpStatus.NOT_FOUND, com.example.EmployeeManagement.utils.ErrorCode.RESOURCE_NOT_FOUND, message, args);
    }
}
