package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.PermissionCreateDTO;
import com.example.EmployeeManagement.dto.response.PermissionResponseDTO;
import com.example.EmployeeManagement.dto.request.PermissionUpdateDTO;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.Permission;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.PermissionMapper;
import com.example.EmployeeManagement.repository.PermissionRepository;
import com.example.EmployeeManagement.service.PermissionService;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImp implements PermissionService {

    private final PermissionRepository repository;
    private final PermissionMapper mapper;

    public PermissionServiceImp(PermissionRepository repository, PermissionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Permission findByAccess(String access) {
        return repository.findByAccess(access)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with access {0}", access));
    }

    @Override
    @Transactional
    public Response<PermissionResponseDTO> create(PermissionCreateDTO dto) {

        // Application-level validation
        if (repository.existsByAccess(dto.access())) {
            throw new IllegalArgumentException("This access already exists");
        }
        Permission permission = mapper.fromCreate(dto);

        repository.save(permission);

        return Response.ok(mapper.toDTO(permission));
    }

    @Override
    @Transactional
    public Response<PermissionResponseDTO> update(Long id, PermissionUpdateDTO dto) {
        Permission permission = find(id);

        // Agar access o'zgarsa uniqueness tekshirish
        if (dto.access() != null &&
                repository.existsByAccessAndIdNot(dto.access(), id)) {
            throw new IllegalArgumentException("Permission access already exists");
        }

        mapper.fromUpdate(dto, permission);

        repository.save(permission);

        return Response.ok(mapper.toDTO(permission));
    }

    @Override
    @Transactional
    public Response<PermissionResponseDTO> delete(Long id) {
        Permission permission = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found or already deleted with id: " + id));

        permission.setDeleted(true);
        Permission save = repository.save(permission);

        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Response<PermissionResponseDTO> findById(Long id) {
        return Response.ok(mapper.toDTO(find(id)));
    }

    @Override
    public Response<List<PermissionResponseDTO>> findAll() {
        return Response.ok(
                repository.findAllActive()
                        .stream()
                        .map(mapper::toDTO)
                        .toList()
        );
    }

    private Permission find(Long id) {
        return repository.findActiveById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Permission not found with id {0}", id));
    }
}
