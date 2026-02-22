package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.request.EmployeeCreateDTO;
import com.example.EmployeeManagement.dto.request.EmployeeUpdateDTO;
import com.example.EmployeeManagement.dto.response.EmployeeResponseDTO;
import com.example.EmployeeManagement.entity.Employee;

public interface EmployeeService
        extends GenericCrudService <Long, Employee, EmployeeResponseDTO, EmployeeCreateDTO, EmployeeUpdateDTO> {

    Employee find(Long id);

}
