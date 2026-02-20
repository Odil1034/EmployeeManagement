package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.DepartmentCreateDTO;
import com.example.EmployeeManagement.dto.request.DepartmentUpdateDTO;
import com.example.EmployeeManagement.dto.response.DepartmentResponseDTO;
import com.example.EmployeeManagement.entity.Department;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.enums.DepartmentStatus;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.DepartmentMapper;
import com.example.EmployeeManagement.repository.DepartmentRepository;
import com.example.EmployeeManagement.service.DepartmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    @Override
    public Response<DepartmentResponseDTO> findByName(String name) {
        Department department = repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with name: {0}", name));
        return Response.ok(mapper.toDTO(department));
    }

    @Transactional
    @Override
    public Response<Boolean> changeDepartmentStatus(Long id, DepartmentStatus status) {
        int i = repository.updateStatus(id, status.name());
        return Response.ok(i > 0);
    }

    @Override
    public Response<DepartmentResponseDTO> create(DepartmentCreateDTO dto) {
        if (repository.existsByName(dto.name())) {
            throw new RuntimeException("The department by this name " + dto.name() + " already exists. Please write another department name");
        }
        Department department = mapper.fromCreate(dto);
        repository.save(department);

        return Response.ok(mapper.toDTO(department));
    }

    @Override
    public Response<DepartmentResponseDTO> update(Long id, DepartmentUpdateDTO dto) {
        Department department = find(id);
        mapper.fromUpdate(dto, department);

        repository.save(department);
        return Response.ok(mapper.toDTO(department));
    }

    @Override
    @Transactional
    public Response<DepartmentResponseDTO> delete(Long id) {
        Department department = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found or already deleted with id: " + id));

        department.setDeleted(true);
        Department save = repository.save(department);

        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Department find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: {0}", id));
    }

    @Override
    public Response<DepartmentResponseDTO> findById(Long id) {
        return Response.ok(mapper.toDTO(find(id)));
    }

    @Override
    public Response<List<DepartmentResponseDTO>> findAll() {
        return Response.ok(repository.findAllActive().stream()
                .map(mapper::toDTO)
                .toList());
    }
}
