package com.example.EmployeeManagement.exception;

import org.springframework.http.HttpStatus;
import com.example.EmployeeManagement.utils.ErrorCode;

/**
 * @author Baxriddinov Odiljon
 * @since 11/February/2026  15:19
 **/
public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message, Object... args) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message, args);
    }
}
