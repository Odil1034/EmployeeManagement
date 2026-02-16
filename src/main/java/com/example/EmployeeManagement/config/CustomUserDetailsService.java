package com.example.EmployeeManagement.config;

import com.example.EmployeeManagement.entity.Permission;
import com.example.EmployeeManagement.entity.Role;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameNative(username).orElseThrow(() ->
                        new UsernameNotFoundException("Username not found: " + username));

        List<Object[]> rows = userRepository.findRolesWithPermissions(username);

        Map<Long, Role> roleMap = new HashMap<>();

        for (Object[] row : rows) {
            Long roleId = ((Number) row[0]).longValue();
            String roleName = (String) row[1];
            Long permId = row[2] != null ? ((Number) row[2]).longValue() : null;
            String permName = (String) row[3];
            String permDesc = (String) row[4];

            Role role = roleMap.computeIfAbsent(roleId, id -> {
                Role r = new Role();
                r.setId(roleId);
                r.setName(roleName);
                r.setPermissions(new HashSet<>());
                return r;
            });

            if (permId != null) {
                Permission permission = new Permission();
                permission.setId(permId);
                permission.setAccess(permName);
                permission.setDescription(permDesc);
                role.getPermissions().add(permission);
            }
        }

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isAccountNonLocked(),
                new HashSet<>(roleMap.values())
        );
    }
}
