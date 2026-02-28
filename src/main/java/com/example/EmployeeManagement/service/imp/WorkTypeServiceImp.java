package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.WorkTypeCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkTypeUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkTypeResponseDTO;
import com.example.EmployeeManagement.entity.work.WorkType;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.WorkTypeMapper;
import com.example.EmployeeManagement.repository.WorkTypeRepository;
import com.example.EmployeeManagement.service.WorkTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkTypeServiceImp implements WorkTypeService {

    private final WorkTypeRepository repository;
    private final WorkTypeMapper mapper;


    @Override
    public List<WorkTypeResponseDTO> getByCategory(Long workCategoryId) {
        return List.of();
    }

    @Override
    public boolean existsByNameAndCategory(String name, Long categoryId) {
        return false;
    }

    @Override
    public List<WorkTypeResponseDTO> search(String keyword) {
        return List.of();
    }

    @Override
    public WorkTypeResponseDTO getDetails(Long id) {
        return null;
    }

    @Override
    public Response<WorkTypeResponseDTO> create(WorkTypeCreateDTO dto) {
        if (repository.existsByName(dto.name())) {
            throw new RuntimeException("Work type by name " + dto.name() + " already exists");
        }
        WorkType work = mapper.fromCreate(dto);
        WorkType save = repository.save(work);

        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Response<WorkTypeResponseDTO> update(Long id, WorkTypeUpdateDTO dto) {
        if (repository.existsByName(dto.name())) {
            throw new RuntimeException("Work type by name " + dto.name() + " already exists");
        }
        WorkType work = find(id);
        mapper.fromUpdate(dto, work);

        WorkType save = repository.save(work);
        return Response.ok(mapper.toDTO(save));
    }

    @Override
    @Transactional
    public Response<WorkTypeResponseDTO> delete(Long id) {
        WorkType workType = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkType not found or already deleted with id: " + id));

        workType.setDeleted(true);
        WorkType save = repository.save(workType);

        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public WorkType find(Long id) {
        return repository.findByIdCustom(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkType not found with id: {0}", id));
    }

    @Override
    public Response<WorkTypeResponseDTO> findById(Long id) {
        return Response.ok(mapper.toDTO(find(id)));
    }

    @Override
    public Response<List<WorkTypeResponseDTO>> findAll() {
        return Response.ok(repository.findAll().stream()
                .map(mapper::toDTO)
                .toList());
    }
}
