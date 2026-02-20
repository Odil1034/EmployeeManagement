package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.DepartmentCreateDTO;
import com.example.EmployeeManagement.dto.request.DepartmentUpdateDTO;
import com.example.EmployeeManagement.dto.response.DepartmentResponseDTO;
import com.example.EmployeeManagement.enums.DepartmentStatus;
import com.example.EmployeeManagement.service.DepartmentService;
import com.example.EmployeeManagement.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_PATH_V1 + "/department")
@Tag(name = "Department Controller", description = "API for managing departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(summary = "Create a new department",
            description = "Creates a new department with name, status and other required fields")
    @PostMapping("/create")
    public ResponseEntity<Response<DepartmentResponseDTO>> create(
            @RequestBody @Parameter(description = "Department creation data", required = true) DepartmentCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(dto));
    }

    @Operation(summary = "Get department by ID",
            description = "Returns department details by its ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<Response<DepartmentResponseDTO>> getById(
            @PathVariable @Parameter(description = "ID of the department to retrieve", required = true) Long id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @Operation(summary = "Delete department by ID",
            description = "Deletes the department with the given ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<DepartmentResponseDTO>> deleteById(
            @PathVariable @Parameter(description = "ID of the department to delete", required = true) Long id) {
        return ResponseEntity.ok(departmentService.delete(id));
    }

    @Operation(summary = "Update department by ID",
            description = "Updates department details. Requires all fields for full update (PUT semantics)")
    @PutMapping("/update/{id}")
    public ResponseEntity<Response<DepartmentResponseDTO>> updateById(
            @PathVariable @Parameter(description = "ID of the department to update", required = true) Long id,
            @RequestBody @Parameter(description = "Updated department data", required = true) DepartmentUpdateDTO dto) {
        return ResponseEntity.ok(departmentService.update(id, dto));
    }

    @Operation(summary = "Change department status",
            description = "Partially updates the status of the department (PATCH semantics). Only status field is required")
    @PatchMapping("/update/status/{id}")
    public ResponseEntity<Response<Boolean>> updateStatus(
            @PathVariable @Parameter(description = "ID of the department to update status", required = true) Long id,
            @RequestBody @Parameter(description = "New department status", required = true) DepartmentStatus status) {
        return ResponseEntity.ok(departmentService.changeDepartmentStatus(id, status));
    }

    @Operation(summary = "Get all departments",
            description = "Returns a list of all departments")
    @GetMapping("/all")
    public ResponseEntity<Response<List<DepartmentResponseDTO>>> getAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }
}
