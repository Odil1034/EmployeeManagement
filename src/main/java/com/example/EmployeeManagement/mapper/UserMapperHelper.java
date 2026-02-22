package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.entity.*;
import com.example.EmployeeManagement.repository.PermissionRepository;
import com.example.EmployeeManagement.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapperHelper {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public Set<Role> toRoleSet(Set<Long> roleIds) {
        if (roleIds == null) return new HashSet<>();
        return roleIds.stream()
                .map(id -> roleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + id)))
                .collect(Collectors.toSet());
    }

    public Set<Role> toRoles(Set<String> roleStrs){
        Set<Role> roles = new HashSet<>();
        for (String roleStr : roleStrs) {
            roles.add(roleRepository.findByName(roleStr).get());
        }
        return roles;
    }

    public Set<String> toRoleStrSet(Set<Role> roles) {
        if (roles == null) return new HashSet<>();
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    public Set<Permission> toPermissionSet(Set<Long> permissionIds) {
        if (permissionIds == null) return new HashSet<>();
        return permissionIds.stream()
                .map(id -> permissionRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Permission not found: " + id)))
                .collect(Collectors.toSet());
    }

    public Set<String> toPermissionStrSet(Set<Permission> perms) {
        if (perms == null) return new HashSet<>();
        return perms.stream()
                .map(Permission::getAccess)
                .collect(Collectors.toSet());
    }

    public Employee mapEmployee(Long id) {
        if (id == null) return null;

        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }

    public Shift mapShift(Long id) {
        if (id == null) return null;

        Shift shift = new Shift();
        shift.setId(id);
        return shift;
    }

    public ShiftAssignment mapShiftAssignment(Long id) {
        if (id == null) return null;

        ShiftAssignment shiftAssignment = new ShiftAssignment();
        shiftAssignment.setId(id);
        return shiftAssignment;
    }
}
