package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.WorkCategoryCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkCategoryUpdateDTO;
import com.example.EmployeeManagement.dto.response.SimpleWorkTypeDTO;
import com.example.EmployeeManagement.dto.response.WorkCategoryResponseDTO;
import com.example.EmployeeManagement.entity.work.WorkCategory;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.WorkCategoryMapper;
import com.example.EmployeeManagement.repository.WorkCategoryRepository;
import com.example.EmployeeManagement.service.WorkCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkCategoryServiceImp implements WorkCategoryService {

    private final WorkCategoryRepository repository;
    private final WorkCategoryMapper mapper;

    @Override
    public Response<List<WorkCategoryResponseDTO>> getByAllowedPosition(Long positionId) {
        return null;
    }

    @Override
    public Response<Boolean> existsByName(String name) {
        return null;
    }

    @Override
    public Response<List<WorkCategoryResponseDTO>> search(String keyword) {
        return null;
    }

    @Override
    public Response<List<WorkCategoryResponseDTO>> getAllActive() {
        return null;
    }

    @Override
    public Response<List<SimpleWorkTypeDTO>> getWorkTypes(Long categoryId) {
        return null;
    }

    @Override
    public Response<WorkCategoryResponseDTO> create(WorkCategoryCreateDTO dto) {
        if (repository.existsByName(dto.name())) {
            throw new RuntimeException("Work category by name " + dto.name() + " already exists");
        }
        WorkCategory work = mapper.fromCreate(dto);
        WorkCategory save = repository.save(work);

        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Response<WorkCategoryResponseDTO> update(Long id, WorkCategoryUpdateDTO dto) {
        if (repository.existsByName(dto.name())) {
            throw new RuntimeException("Work category by name " + dto.name() + " already exists");
        }
        WorkCategory work = find(id);
        mapper.fromUpdate(dto, work);

        WorkCategory save = repository.save(work);
        return Response.ok(mapper.toDTO(save));
    }

    @Override
    @Transactional
    public Response<WorkCategoryResponseDTO> delete(Long id) {
        WorkCategory workType = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkCategory not found or already deleted with id: " + id));

        workType.setDeleted(true);
        WorkCategory save = repository.save(workType);

        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public WorkCategory find(Long id) {
        return repository.findByIdCustom(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkCategory not found with id: {0}", id));
    }

    @Override
    public Response<WorkCategoryResponseDTO> findById(Long id) {
        WorkCategory entity = find(id);
        return Response.ok(mapper.toDTO(entity));
    }

    @Override
    public Response<List<WorkCategoryResponseDTO>> findAll() {
        return Response.ok(repository.findAll().stream()
                .map(mapper::toDTO)
                .toList());
    }
}
