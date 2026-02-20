package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.response.RoleResponseDTO;
import com.example.EmployeeManagement.service.RoleService;
import com.example.EmployeeManagement.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/get")
    public ResponseEntity<Response<RoleResponseDTO>> getRoleByName(@RequestParam String name) {
        return ResponseEntity.ok(roleService.getRoleWithPermissions(name));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<RoleResponseDTO>>> getAllRoles(){
        return ResponseEntity.ok(roleService.findAll());
    }
}
