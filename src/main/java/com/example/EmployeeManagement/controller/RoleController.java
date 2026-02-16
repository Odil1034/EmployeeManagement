package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.entity.Role;
import com.example.EmployeeManagement.service.RoleService;
import com.example.EmployeeManagement.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{name}")
    public Role getRole(@PathVariable String name) {
        return roleService.getRoleWithPermissions(name);
    }
}
