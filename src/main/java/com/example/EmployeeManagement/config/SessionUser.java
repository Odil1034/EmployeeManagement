package com.example.EmployeeManagement.config;

import com.example.EmployeeManagement.service.RoleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SessionUser {
    private RoleService roleService;

    public Optional<CustomUserDetails> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof CustomUserDetails user)) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public Long getID() {
        return getUser().map(CustomUserDetails::getId).orElse(-1L);
    }
}
