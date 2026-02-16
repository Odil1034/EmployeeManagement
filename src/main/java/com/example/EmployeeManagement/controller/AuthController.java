package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.request.UserCreateDTO;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.response.UserResponseDTO;
import com.example.EmployeeManagement.dto.auth.RefreshTokenRequestDTO;
import com.example.EmployeeManagement.dto.auth.TokenRequestDTO;
import com.example.EmployeeManagement.dto.auth.TokenResponseDTO;
import com.example.EmployeeManagement.service.UserService;
import com.example.EmployeeManagement.service.token.TokenService;
import com.example.EmployeeManagement.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping(value = "/token")
    public ResponseEntity<Response<TokenResponseDTO>> login(@Valid @RequestBody TokenRequestDTO dto) {
        return ResponseEntity.ok(tokenService.generateToken(dto.username(), dto.password()));
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<Response<TokenResponseDTO>> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO dto) {
        return ResponseEntity.ok(tokenService.refreshToken(dto.refreshToken()));
    }

    @PostMapping(value = "/register")
    private ResponseEntity<Response<UserResponseDTO>> register(@Valid @RequestBody UserCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }
}
