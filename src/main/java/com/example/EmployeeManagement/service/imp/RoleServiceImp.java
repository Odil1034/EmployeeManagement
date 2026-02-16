package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.ErrorResponse;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.RoleCreateDTO;
import com.example.EmployeeManagement.dto.response.RoleResponseDTO;
import com.example.EmployeeManagement.dto.response.RoleUpdateDTO;
import com.example.EmployeeManagement.entity.Role;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.RoleMapper;
import com.example.EmployeeManagement.repository.PermissionRepository;
import com.example.EmployeeManagement.repository.RoleRepository;
import com.example.EmployeeManagement.service.RoleService;
import com.example.EmployeeManagement.utils.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper = RoleMapper.ROLE_MAPPER;

    @Override
    public Role findByName(String roleName) {
        return repository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with roleName {}", roleName));
    }

    @Override
    public Role getRoleWithPermissions(String roleName) {
        return repository.findRoleWithPermissionsByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    @Transactional
    public Response<RoleResponseDTO> create(RoleCreateDTO dto) {
        if (repository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Role already exists");
        }
        Role role = mapper.fromCreate(dto);
        System.out.println(role);

        return Response.ok(mapper.toDTO(repository.save(role)));
    }

    @Override
    @Transactional
    public Response<RoleResponseDTO> update(Long id, RoleUpdateDTO dto) {
        Role role = repository.findByIdCustom(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id {0}", id));
        mapper.fromUpdate(dto, role);
        return Response.ok(mapper.toDTO(role));
    }

    @Override
    @Transactional
    public Response<Boolean> delete(Long id) {
        Role role = find(id);repository.delete(role);
        return Response.ok(true);
    }

    @Override
    public Response<RoleResponseDTO> findById(Long id) {
        Role role = find(id);
        return Response.ok(mapper.toDTO(role));
    }

    private Role find(Long id){
        return repository.findByIdCustom(id).orElseThrow(
                    () -> new ResourceNotFoundException("Role not found with id {0}", id));
    }

    @Override
    public Response<List<RoleResponseDTO>> findAll() {
        return Response.ok(
                repository.findAllWithPermissions()
                        .stream()
                        .map(mapper::toDTO)
                        .toList()
        );
    }
}
