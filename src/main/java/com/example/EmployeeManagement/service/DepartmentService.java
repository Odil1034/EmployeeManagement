package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.DepartmentCreateDTO;
import com.example.EmployeeManagement.dto.request.DepartmentUpdateDTO;
import com.example.EmployeeManagement.dto.response.DepartmentResponseDTO;
import com.example.EmployeeManagement.entity.Department;
import com.example.EmployeeManagement.enums.DepartmentStatus;

public interface DepartmentService extends GenericCrudService
        <Long, Department, DepartmentResponseDTO, DepartmentCreateDTO, DepartmentUpdateDTO> {

    Response<DepartmentResponseDTO> findByName(String name);

    Response<Boolean> changeDepartmentStatus(Long id, DepartmentStatus departmentStatus);

    Department find(Long id);

}
