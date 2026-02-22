package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.ShiftAssignmentCreateDTO;
import com.example.EmployeeManagement.dto.request.ShiftAssignmentUpdateDTO;
import com.example.EmployeeManagement.dto.response.ShiftAssignmentResponseDTO;
import com.example.EmployeeManagement.entity.ShiftAssignment;

import java.time.LocalDate;
import java.util.List;

public interface ShiftAssignmentService extends GenericCrudService
        <Long, ShiftAssignment, ShiftAssignmentResponseDTO, ShiftAssignmentCreateDTO, ShiftAssignmentUpdateDTO> {

    ShiftAssignment find(Long id);

    Response<List<ShiftAssignmentResponseDTO>> findByEmployeeId(Long employeeId);

    Response<List<ShiftAssignmentResponseDTO>> getByEmployeeFullName(String fullName);

    Response<List<ShiftAssignmentResponseDTO>> findByDate(LocalDate localDate);

    Response<List<ShiftAssignmentResponseDTO>> findByShiftId(Long shiftId);

    Response<List<ShiftAssignmentResponseDTO>> findAllByDepartmentId(Long departmentId);

    Response<List<ShiftAssignmentResponseDTO>> findByShiftName(String shiftName);
}

