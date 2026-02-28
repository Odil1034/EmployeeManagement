package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.WorkCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkEmpUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkResponseDTO;
import com.example.EmployeeManagement.entity.work.Work;

import java.math.BigDecimal;
import java.util.List;

public interface WorkService
        extends GenericCrudService<Long, Work, WorkResponseDTO, WorkCreateDTO, WorkEmpUpdateDTO> {

    Work find(Long id);

    Response<List<WorkResponseDTO>> getByEmployee(Long employeeId);

    Response<List<WorkResponseDTO>> getByWorkType(Long workTypeId);

    Response<List<WorkResponseDTO>> getByCategory(Long workCategoryId);

    Response<List<WorkResponseDTO>> search(String keyword);

    Response<BigDecimal> calculateTotal(Long workId);

}
