package com.example.EmployeeManagement.config;

import com.example.EmployeeManagement.entity.Permission;
import com.example.EmployeeManagement.entity.Role;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.repository.PermissionRepository;
import com.example.EmployeeManagement.repository.RoleRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameNative(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found: " + username));

        Set<Role> userRoles = roleRepository.findRolesByUsername(username);
        Set<Permission> userPermissions = permissionRepository.findPermissionsByUsername(username);

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (Role role : userRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        for (Permission perm : userPermissions) {
            authorities.add(new SimpleGrantedAuthority(perm.getAccess()));
        }

        user.setRoles(userRoles);
        return new CustomUserDetails(user.getId(), username, user.getPassword(), user.isAccountNonLocked(), authorities);
    }
}
