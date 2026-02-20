package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.RoleCreateDTO;
import com.example.EmployeeManagement.dto.response.RoleResponseDTO;
import com.example.EmployeeManagement.dto.response.RoleUpdateDTO;
import com.example.EmployeeManagement.dto.response.UserDTO;
import com.example.EmployeeManagement.entity.Role;

public interface RoleService extends GenericCrudService
        <Long, Role, RoleResponseDTO, RoleCreateDTO, RoleUpdateDTO> {

    Role findByName(String roleName);

    Response<RoleResponseDTO> getRoleWithPermissions(String roleName);

    boolean hasRole(Long id, String roleName);

    Response<UserDTO> assignRoleToUser(String roleName, Long userId);

}
