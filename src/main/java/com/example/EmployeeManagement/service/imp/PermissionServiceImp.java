package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.PermissionCreateDTO;
import com.example.EmployeeManagement.dto.response.PermissionResponseDTO;
import com.example.EmployeeManagement.dto.request.PermissionUpdateDTO;
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
@RequiredArgsConstructor
public class PermissionServiceImp implements PermissionService {

    private final PermissionRepository repository;
    private final PermissionMapper mapper;

    @Override
    public Permission findByName(String name) {
        return repository.findByAccess(name)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with name {}", name));
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
    public Response<Boolean> delete(Long id) {
        Permission permission = find(id);

        int updated = repository.softDelete(permission.getId());

        return Response.ok(true);
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
