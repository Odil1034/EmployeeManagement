package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.request.PositionCreateDTO;
import com.example.EmployeeManagement.dto.request.PositionUpdateDTO;
import com.example.EmployeeManagement.dto.response.PositionResponseDTO;
import com.example.EmployeeManagement.dto.response.SimpleEmployeeDTO;
import com.example.EmployeeManagement.entity.Position;

import java.util.List;

public interface PositionService
        extends GenericCrudService<Long, Position, PositionResponseDTO, PositionCreateDTO, PositionUpdateDTO> {

    List<PositionResponseDTO> getPositionsByDepartment(Long departmentId);

    // Positionga allowed works biriktirish
    void assignWorkCategories(Long positionId, List<Long> workCategoryIds);

    boolean existsByName(String name);

    List<SimpleEmployeeDTO> getEmployees(Long positionId);

    List<PositionResponseDTO> search(String keyword);
}
