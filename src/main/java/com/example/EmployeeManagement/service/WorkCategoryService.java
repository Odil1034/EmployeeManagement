package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.WorkCategoryCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkCategoryUpdateDTO;
import com.example.EmployeeManagement.dto.response.SimpleWorkTypeDTO;
import com.example.EmployeeManagement.dto.response.WorkCategoryResponseDTO;
import com.example.EmployeeManagement.entity.work.WorkCategory;

import java.util.List;

public interface WorkCategoryService
        extends GenericCrudService<Long, WorkCategory, WorkCategoryResponseDTO, WorkCategoryCreateDTO, WorkCategoryUpdateDTO> {

    Response<List<WorkCategoryResponseDTO>> getByAllowedPosition(Long positionId);

    Response<Boolean> existsByName(String name);

    Response<List<WorkCategoryResponseDTO>> search(String keyword);

    Response<List<WorkCategoryResponseDTO>> getAllActive();

    Response<List<SimpleWorkTypeDTO>> getWorkTypes(Long categoryId);

    WorkCategory find(Long id);
}
