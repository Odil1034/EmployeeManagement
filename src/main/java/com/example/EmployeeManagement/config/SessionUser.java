package com.example.EmployeeManagement.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SessionUser {

    public Optional<CustomUserDetails> getUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return Optional.empty();
        } else if (authentication.getPrincipal() instanceof CustomUserDetails ud) {
            return Optional.of(ud);
        }
        return Optional.empty();
    }

    public Long getID() {
        return getUser().map(CustomUserDetails::id).orElse(-1L);
    }
}
