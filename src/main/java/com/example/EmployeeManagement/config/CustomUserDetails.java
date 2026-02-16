package com.example.EmployeeManagement.config;

import com.example.EmployeeManagement.entity.Permission;
import com.example.EmployeeManagement.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public record CustomUserDetails(Long id, String username, String password, boolean isAccountNonLocked,
                                Set<Role> roles) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (roles == null) {
            return authorities;
        }

        for (Role role : roles) {
            if (role.getName() != null) {
                String roleName = role.getName().startsWith("ROLE_")
                        ? role.getName()
                        : "ROLE_" + role.getName();
                authorities.add(new SimpleGrantedAuthority(roleName));
            }

            if (role.getPermissions() != null) {
                for (Permission permission : role.getPermissions()) {
                    if (permission.getAccess() != null) {
                        authorities.add(new SimpleGrantedAuthority(permission.getAccess()));
                    }
                }
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}