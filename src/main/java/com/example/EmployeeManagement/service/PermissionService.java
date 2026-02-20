package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.request.PermissionCreateDTO;
import com.example.EmployeeManagement.dto.response.PermissionResponseDTO;
import com.example.EmployeeManagement.dto.request.PermissionUpdateDTO;
import com.example.EmployeeManagement.entity.Permission;

import java.util.Optional;

public interface PermissionService extends GenericCrudService
        <Long, Permission, PermissionResponseDTO, PermissionCreateDTO, PermissionUpdateDTO> {

    Permission findByAccess(String access);

}
