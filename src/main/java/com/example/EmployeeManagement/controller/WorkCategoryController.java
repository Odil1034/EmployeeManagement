package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.WorkCategoryCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkCategoryUpdateDTO;
import com.example.EmployeeManagement.dto.request.WorkCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkEmpUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkCategoryResponseDTO;
import com.example.EmployeeManagement.dto.response.WorkResponseDTO;
import com.example.EmployeeManagement.entity.work.WorkCategory;
import com.example.EmployeeManagement.service.WorkCategoryService;
import com.example.EmployeeManagement.service.WorkService;
import com.example.EmployeeManagement.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/work-category")
@RequiredArgsConstructor
public class WorkCategoryController {

    private final WorkCategoryService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<WorkCategoryResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<WorkCategoryResponseDTO>>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Response<WorkCategoryResponseDTO>> create(@Valid @RequestBody WorkCategoryCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Response<WorkCategoryResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody WorkCategoryUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<WorkCategoryResponseDTO>> delete(@PathVariable Long id){
        return ResponseEntity.ok(service.delete(id));
    }
}
