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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImp implements PermissionService {

    @Autowired
    public PermissionRepository repository;
    PermissionMapper mapper = PermissionMapper.PERMISSION_MAPPER;

    @Override
    public Permission findByName(String permissionName) {
        return repository.findByName(permissionName)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with permissionName {}", permissionName));
    }

    @Override
    @Transactional
    public Response<PermissionResponseDTO> create(PermissionCreateDTO dto) {


        // Permission nomi unique bo'lishini tekshirish
        if (repository.existsByAccess(dto.access())) {
            throw new IllegalArgumentException("Permission already exists");
        }
        Permission newPermission = mapper.fromCreate(dto);
        System.out.println(newPermission);
        repository.save(newPermission);
        return Response.ok(mapper.toDTO(newPermission));
    }

    @Override
    @Transactional
    public Response<PermissionResponseDTO> update(Long id, PermissionUpdateDTO dto) {
        Permission permission = find(id);

    }

    @Override
    @Transactional
    public Response<Boolean> delete(Long id) {
        repository.softDelete(id);
        return Response.ok(true);
    }

    @Override
    public Response<PermissionResponseDTO> findById(Long id) {
        Permission permission = repository.findByIdCustom(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id {0}", id));
        return Response.ok(mapper.toDTO(permission));
    }

    private Permission find(Long id){

    }

    @Override
    public Response<List<PermissionResponseDTO>> findAll() {
        return Response.ok(
                repository.findAllCustom()
                        .stream()
                        .map(mapper::toDTO)
                        .toList()
        );
    }
}
