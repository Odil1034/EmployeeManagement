package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.request.WorkTypeCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkTypeUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkTypeResponseDTO;
import com.example.EmployeeManagement.entity.work.WorkType;

import java.util.List;

public interface WorkTypeService
        extends GenericCrudService<Long, WorkType, WorkTypeResponseDTO, WorkTypeCreateDTO, WorkTypeUpdateDTO> {

    List<WorkTypeResponseDTO> getByCategory(Long workCategoryId);

    boolean existsByNameAndCategory(String name, Long categoryId);

    List<WorkTypeResponseDTO> search(String keyword);

    WorkTypeResponseDTO getDetails(Long id);

    WorkType find(Long id);
}
