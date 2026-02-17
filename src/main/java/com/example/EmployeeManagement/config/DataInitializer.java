//package com.example.EmployeeManagement.config;
//
//import com.example.EmployeeManagement.entity.Permission;
//import com.example.EmployeeManagement.entity.Role;
//import com.example.EmployeeManagement.enums.DefaultPermission;
//import com.example.EmployeeManagement.enums.DefaultRole;
//import com.example.EmployeeManagement.repository.PermissionRepository;
//import com.example.EmployeeManagement.repository.RoleRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer {
//
//    private final RoleRepository roleRepository;
//    private final PermissionRepository permissionRepository;
//
//    @PostConstruct
//    @Transactional
//    public void init() {
//        initPermissions();
//        initRoles();
//    }
//
//    private void initPermissions() {
//        for (DefaultPermission defaultPermission : DefaultPermission.values()) {
//            if (!permissionRepository.existsByAccess(defaultPermission.name())) {
//                Permission permission = Permission.builder()
//                        .access(defaultPermission.name())
//                        .description(defaultPermission.getDescription())
//                        .build();
//                permissionRepository.save(permission);
//            }
//        }
//    }
//
//    private void initRoles() {
//        for (DefaultRole defaultRole : DefaultRole.values()) {
//            if (!roleRepository.existsByName(defaultRole.name())) {
//                // Get permissions for this role
//                Set<Permission> permissions = defaultRole.getPermissions()
//                        .stream()
//                        .map(p -> permissionRepository.findByAccess(p.name())
//                                .orElseThrow(() -> new RuntimeException(
//                                        "Permission not found: " + p.name())))
//                        .collect(java.util.stream.Collectors.toSet());
//
//                Role role = Role.builder()
//                        .name(defaultRole.name())
//                        .description(defaultRole.getDescription())
//                        .permissions(permissions)
//                        .build();
//
//                roleRepository.save(role);
//            }
//        }
//    }
//}
