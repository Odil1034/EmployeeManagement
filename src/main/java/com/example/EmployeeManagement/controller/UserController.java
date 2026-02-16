package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.PasswordUpdateDTO;
import com.example.EmployeeManagement.dto.response.UserResponseDTO;
import com.example.EmployeeManagement.service.UserService;
import com.example.EmployeeManagement.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<UserResponseDTO>> profile() {
        return ResponseEntity.ok(userService.profile());
    }

    @PutMapping("/update/password")
    ResponseEntity<Response<UserResponseDTO>> updatePassword(@Valid @RequestBody PasswordUpdateDTO dto) {
        return ResponseEntity.ok(userService.updatePassword(dto));
    }
}
