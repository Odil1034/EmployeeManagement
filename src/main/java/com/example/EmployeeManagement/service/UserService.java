package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.request.PasswordUpdateDTO;
import com.example.EmployeeManagement.dto.request.UserUpdateDTO;
import com.example.EmployeeManagement.dto.request.UserCreateDTO;
import com.example.EmployeeManagement.dto.response.UserResponseDTO;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserService extends GenericCrudService
        <Long, User, UserResponseDTO, UserCreateDTO, UserUpdateDTO> {

    Optional<User> findByUsername(@NotNull String username);

    Response<UserResponseDTO> profile();

    Response<String> updatePassword(PasswordUpdateDTO dto);

    User find(Long id);

}
