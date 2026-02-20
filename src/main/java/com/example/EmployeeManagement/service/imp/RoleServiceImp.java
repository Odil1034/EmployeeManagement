package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.RoleCreateDTO;
import com.example.EmployeeManagement.dto.response.RoleResponseDTO;
import com.example.EmployeeManagement.dto.response.RoleUpdateDTO;
import com.example.EmployeeManagement.dto.response.UserDTO;
import com.example.EmployeeManagement.entity.Role;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.RoleMapper;
import com.example.EmployeeManagement.repository.RoleRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import com.example.EmployeeManagement.service.RoleService;
import com.example.EmployeeManagement.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final RoleRepository repository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleMapper mapper;

    // ================= FIND BY NAME =================

    @Override
    @Transactional
    public Role findByName(String roleName) {
        return repository.findByName(roleName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with name: " + roleName));
    }

    // ================= ASSIGN ROLE =================

    @Override
    @Transactional
    public Response<UserDTO> assignRoleToUser(String roleName, Long userId) {

        User user = userService.find(userId);
        Role role = findByName(roleName);

        Set<Role> roles = user.getRoles();
        if (roles == null) {
            roles = new HashSet<>();
        }

        if (roles.contains(role)) {
            throw new IllegalStateException("User already has this role");
        }

        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        Set<String> roleNames = roles.stream()
                .map(Role::getName)
                .collect(HashSet::new, HashSet::add, HashSet::addAll);

        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), roleNames);

        return Response.ok(userDTO);
    }

    // ================= GET ROLE WITH PERMISSIONS =================

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Response<RoleResponseDTO> getRoleWithPermissions(String roleName) {

        Role role = repository.findRoleWithPermissionsByName(roleName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with name: " + roleName));

        return Response.ok(mapper.toDTO(role));
    }

    // ================= CHECK USER HAS ROLE =================

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public boolean hasRole(Long userId, String roleName) {

        User user = userService.find(userId);
        Role role = findByName(roleName);

        return user.getRoles().contains(role);
    }

    // ================= CREATE =================

    @Override
    @Transactional
    public Response<RoleResponseDTO> create(RoleCreateDTO dto) {

        if (repository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Role already exists with name: " + dto.name());
        }

        Role role = mapper.fromCreate(dto);

        return Response.ok(mapper.toDTO(repository.save(role)));
    }

    // ================= UPDATE =================

    @Override
    @Transactional
    public Response<RoleResponseDTO> update(Long id, RoleUpdateDTO dto) {

        Role role = find(id);

        mapper.fromUpdate(dto, role);

        return Response.ok(mapper.toDTO(repository.save(role)));
    }

    // ================= DELETE =================

    @Override
    @Transactional
    public Response<Boolean> delete(Long id) {

        Role role = find(id);

        // Agar production darajada bo‘lsa:
        // if (!role.getUsers().isEmpty()) {
        //     throw new IllegalStateException("Role is assigned to users and cannot be deleted");
        // }

        repository.softDelete(id);

        return Response.ok(true);
    }

    // ================= FIND BY ID =================

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Response<RoleResponseDTO> findById(Long id) {

        return Response.ok(mapper.toDTO(find(id)));
    }

    private Role find(Long id) {
        return repository.findByIdCustom(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + id));
    }

    // ================= FIND ALL =================

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Response<List<RoleResponseDTO>> findAll() {

        List<RoleResponseDTO> roles = repository.findAllWithPermissions()
                .stream()
                .map(mapper::toDTO)
                .toList();

        return Response.ok(roles);
    }
}