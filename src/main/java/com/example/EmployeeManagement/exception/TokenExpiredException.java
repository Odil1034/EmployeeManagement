package com.example.EmployeeManagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.example.EmployeeManagement.utils.ErrorCode;

/**
 * @author Baxriddinov Odiljon
 * @since 11/February/2026  15:19
 **/
@Getter
public class TokenExpiredException extends BaseException {

    public TokenExpiredException(String message, Object... args) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_EXPIRED, message, args);
    }
}

